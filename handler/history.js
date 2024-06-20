const { getUserIdFromEmail, getUserData } = require('../object/firestore');

const getAllHistory = async (req, res) => {
    const { user } = req;
    let { page, size } = req.query;

    if (!page || page < 1) {
        page = 1;
    } else {
        page = parseInt(page);
    }
    if (!size || size < 1) {
        size = 10;
    } else {
        size = parseInt(size);
    }
    const startIndex = (page - 1) * size;
    const endIndex = startIndex + size;

    try {
        const idUser = await getUserIdFromEmail(user.email);
        let { history } = await getUserData(idUser);
        let summaryHistory = [];
        if (history) {
            history = history.slice(startIndex, endIndex);
            summaryHistory = history.map((entry) => ({
                id: entry.id,
                imageUrl: entry.imageUrl,
                predictionResult: entry.predictionResult,
            }));
        }
        return res.json({
            status: 'success',
            message: 'History retreived successfully',
            result: summaryHistory || [],
        });
    } catch (error) {
        return res.json({
            status: 'failed',
            message: 'Internal server error',
        });
    }
}

const getHistoryById = async (req, res) => {
    const { user } = req;
    const { historyId } = req.params;
    try {
        const idUser = await getUserIdFromEmail(user.email);
        const { history } = await getUserData(idUser);
        let specificHistory = null;
        if (history) {
            specificHistory = history.find((entry) => {
                return entry.id == historyId;
            });
        }
        if (!specificHistory) {
            return res.status(404).json({
                status: 'failed',
                message: 'History not found',
            });
        }
        return res.json({
            status: 'success',
            message: 'Detail history retreived successfully',
            result: specificHistory,
        })
    } catch (error) {
        return res.json({
            status: 'failed',
            message: 'Internal server error',
        });
    }
}

module.exports = {
    getAllHistory,
    getHistoryById,
}