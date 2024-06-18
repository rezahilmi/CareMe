require('dotenv').config();
const bcrypt = require('bcrypt');
const crypto = require('crypto');
const { Firestore, FieldValue } = require('@google-cloud/firestore');

const firestore = new Firestore({
    keyFilename: process.env.FIRESTORE_CREDENTIALS,
});

const checkUserExist = async (email) => {
    // console.log((await usersCollection.get()).docs);
    const usersCollection = firestore.collection('users');
    const userSnapshot = await usersCollection.where('email', '==', email).get();
    return !userSnapshot.empty;
}

const checkUserDataValid = async (email, password) => {
    const usersCollection = firestore.collection('users');
    const userSnapshot = await usersCollection.where('email', '==', email).get();
    if (userSnapshot.empty) {
        return false;
    }
    const user = userSnapshot.docs[0].data();
    const validPass = await bcrypt.compare(password, user.password);
    return validPass;
}

const getUserIdFromEmail = async (email) => {
    const usersCollection = firestore.collection('users');
    const userSnapshot = await usersCollection.where('email', '==', email).get();
    return userSnapshot.docs[0].id;
}

const updateUserLastLogin = async (id) => {
    const usersCollection = firestore.collection('users');
    return usersCollection.doc(id).update({
        lastLogin: new Date().toISOString(),
    });
}

const storeUserData = async (id, data) => {
    const usersCollection = firestore.collection('users');
    return usersCollection.doc(id).set({
        ...data,
        id,
        createdAt: new Date().toISOString(),
    }, { merge: true });
}

const getUserData = async (id) => {
    const usersCollection = firestore.collection('users');
    const user = await usersCollection.doc(id).get();
    return user.data();
}

const addPredictResult = async (idUser, data) => {
    const usersCollection = firestore.collection('users');
    const id = crypto.randomBytes(8).toString('hex');
    const historyData = {
        id,
        imageUrl: data.imageUrl,
        result: data.result,
        recommendation: data.recommendation,
        timestamp: new Date().toISOString(),
    };
    return await usersCollection.doc(idUser).update({
        history: FieldValue.arrayUnion(historyData),
    });
}

module.exports = {
    firestore,
    checkUserExist,
    checkUserDataValid,
    updateUserLastLogin,
    storeUserData,
    getUserData,
    getUserIdFromEmail,
    addPredictResult,
};