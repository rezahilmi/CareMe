const { getOrCreateBucket } = require('../object/bucket');
const { addPredictResult, getUserIdFromEmail } = require('../object/firestore');

const getAllHistory = (req, res) => {
    const { user } = req;
    
}

const getHistoryById = () => {

}

module.exports = {
    getAllHistory,
    getHistoryById,
}