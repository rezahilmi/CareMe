const crypto = require('crypto');
const path = require('path');
const { getOrCreateBucket } = require('../object/bucket');
const { addPredictResult, getUserIdFromEmail } = require('../object/firestore');
const { predictModel } = require('./inference');

const predict = async (req, res) => {
    const { file, user } = req;
    if (!file) {
        return res.status(400).json({
            status: 'failed',
            message: 'No image uploaded',
        });
    }
    const ext = path.extname(file.originalname).toLowerCase();
    if (!(ext == '.png' || ext == '.jpg' || ext == '.jpeg')) {
        return res.status(400).json({
            status: 'failed',
            message: 'Only support .png, .jpg, .jpeg file format',
        });
    }
    try {
        let imageUrl = null;
        try {
            const bucket = await getOrCreateBucket();
            const gcsFileName = `uploads/${crypto.randomBytes(16).toString('hex')}`;
            bucket.file(gcsFileName).save(file.buffer, {
                destination: gcsFileName,
                gzip: true,
                metadata: {
                    contentType: file.mimetype,
                }
            });
            imageUrl = `https://storage.googleapis.com/${bucket.name}/${gcsFileName}`;
        } catch (error) {
            return res.status(500).json({
                status: 'failed',
                message: 'Error when trying upload to bucket'
            });
        }

        let prediction = null;
        try {
            prediction = await predictModel(file.buffer);
        } catch (error) {
            return res.status(500).json({
                status: 'failed',
                message: 'Error when trying to get prediction',
            });
        }

        const data = {
            imageUrl,
            predictionResult: prediction.predictionResult,
            description: prediction.description,
            recommendation: prediction.recommendation,
        }
        const idUser = await getUserIdFromEmail(user.email);
        const id = await addPredictResult(idUser, data);
        return res.json({
            status: 'success',
            message: 'Model predicted successfully',
            result: {
                id,
                ...data,
            }
        });
    } catch (error) {
        return res.status(500).json({
            status: 'failed',
            message: 'Internal server error'
        });
    }
};

module.exports = { predict }