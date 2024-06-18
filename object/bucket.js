require('dotenv').config();
const { Storage } = require('@google-cloud/storage');

const storage = new Storage({
    keyFilename: process.env.BUCKET_CREDENTIALS,
});

const getOrCreateBucket = async () => {
    const bucketTarget = storage.bucket(process.env.BUCKET_NAME_USER_UPLOADS);
    const [exist] = await bucketTarget.exists();
    if (!exist) {
        await storage.createBucket(process.env.BUCKET_NAME_USER_UPLOADS);
    }
    return storage.bucket(process.env.BUCKET_NAME_USER_UPLOADS);
};

module.exports = {
    storage,
    getOrCreateBucket,
}