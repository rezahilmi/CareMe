const crypto = require('crypto');
const path = require('path');
const { getOrCreateBucket } = require('../object/bucket');
const { addPredictResult, getUserIdFromEmail } = require('../object/firestore');

// const file = {
//     fieldname: 'image',
//     originalname: '2023-02-19-Selasa-SS.png',
//     encoding: '7bit',
//     mimetype: 'image/png',
//     buffer: 00 00 00 00,
//     size: 638403
// }

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
        const bucket = await getOrCreateBucket();
        const gcsFileName = `uploads/${crypto.randomBytes(16).toString('hex')}`;
        bucket.file(gcsFileName).save(file.buffer, {
            destination: gcsFileName,
            gzip: true,
            metadata: {
                contentType: file.mimetype,
            }
        });
        const imageUrl = `https://storage.googleapis.com/${bucket.name}/${gcsFileName}`;

        // Prediksi

        const data = {
            imageUrl,
            predictionResult: 'Unknown (placeholder)',
            description: 'Unknown (placeholder)',
            recommendation: 'Unknown (placeholder)',
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
            message: 'Error when trying upload to bucket'
        });
    }
};

module.exports = { predict }