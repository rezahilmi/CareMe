require('dotenv').config();
const bcrypt = require('bcrypt');
const { Firestore } = require('@google-cloud/firestore');

const firestore = new Firestore({
    keyFilename: process.env.FIRESTORE_CREDENTIALS,
});

const checkUserExist = async (email) => {
    const usersCollection = firestore.collection('users');
    // console.log((await usersCollection.get()).docs);
    const userSnapshot = await usersCollection.where('email', '==', email).get();
    return !userSnapshot.empty;
}

const checkUserInfoValid = async (email, password) => {
    const usersCollection = firestore.collection('users');
    const userSnapshot = await usersCollection.where('email', '==', email).get();
    if (userSnapshot.empty) {
        return false;
    }
    const user = userSnapshot.docs[0].data();
    const validPass = await bcrypt.compare(password, user.password);
    if (!validPass) {
        return false;
    }
    await storeData(user.id, {
        ...user,
        lastLogin: new Date().toISOString(),
    });
    return true;
}

const storeData = async (id, data) => {
    const usersCollection = firestore.collection('users');
    return usersCollection.doc(id).set(data, { merge: true });
}

const getData = async () => {
    const snapshotPredictions = await firestore.collection('users').get();
    const histories = snapshotPredictions.docs.map((doc) => ({
        id: doc.id,
        history: doc.data()
    }));
    return histories;
}

module.exports = {
    firestore,
    checkUserExist,
    checkUserInfoValid,
    storeData
};