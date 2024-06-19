const { getUserIdFromEmail, getUserData } = require('../object/firestore');

const getAllHistory = async (req, res) => {
    const { user } = req;
    try {
        const idUser = await getUserIdFromEmail(user.email);
        const { history } = await getUserData(idUser);
        let summaryHistory = [];
        if (history) {
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