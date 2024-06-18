require('dotenv').config();
const bcrypt = require('bcrypt');
const { Firestore } = require('@google-cloud/firestore');

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
        return {
            valid: false,
            id: null,
        };
    }
    const user = userSnapshot.docs[0].data();
    const validPass = await bcrypt.compare(password, user.password);
    if (!validPass) {
        return {
            valid: false,
            id: null,
        }
    }
    return {
        valid: true,
        id: user.id,
    };
}

const updateUserLastLogin = async (id) => {
    const usersCollection = firestore.collection('users');
    return usersCollection.doc(id).set({
        lastLogin: new Date().toISOString(),
    }, { merge: true });
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

module.exports = {
    firestore,
    checkUserExist,
    checkUserDataValid,
    updateUserLastLogin,
    storeUserData,
    getUserData
};