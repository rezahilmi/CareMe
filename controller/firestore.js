require('dotenv').config();
const { Firestore } = require('@google-cloud/firestore');

const firestore = new Firestore({
    keyFilename: process.env.FIRESTORE_CREDENTIALS,
});

module.exports = firestore;