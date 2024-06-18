const oauth2Client = require('../object/oauth');
const {
    updateUserLastLogin,
    getUserData,
    storeUserData,
} = require('../object/firestore');
const { createToken } = require('./token');

const loginGoogle = (req, res) => {
    const url = oauth2Client.generateAuthUrl({
        access_type: 'offline',
        scope: [
            'https://www.googleapis.com/auth/userinfo.profile',
            'https://www.googleapis.com/auth/userinfo.email'
        ],
        state: 'login',
    });
    res.redirect(url);
};

const loginGoogleCallback = async (req, res) => {
    const { code, state, error } = req.query;

    if (error) {
        console.log('First error redirect');
        return res.redirect(state == 'login' ? '/login/google' : '/');
    }
    try {
        const { tokens } = await oauth2Client.getToken(code);
        oauth2Client.setCredentials(tokens);
        
        const ticket = await oauth2Client.verifyIdToken({
            idToken: tokens.id_token,
            audience: process.env.CLIENT_ID,
        });
        const payload = ticket.getPayload();
        
        // const createdAt = new Date().toISOString();
        const data = {
            id: payload.sub,
            name: payload.name,
            email: payload.email,
        };
        try {
            await storeUserData(payload.sub, data);
            await updateUserLastLogin(payload.sub);
        } catch (error) {
            res.status(500).json({
                status: 'failed',
                message: 'Something is wrong when inserting data'
            });
        }
        // const userRef = firestore.collection('users').doc(payload.sub);
        // await userRef.set({
        //     id: payload.sub,
        //     displayName: payload.name,
        //     email: payload.email,
        //     picture: payload.picture,
        //     provider: 'google',
        //     lastLogin: new Date().toISOString(),
        // }, { merge: true });
        
        const userData = await getUserData(payload.sub);
        console.log(userData);
        const token = createToken(userData);
        return res.json({
            status: 'success',
            message: 'Login success please use the token',
            token
        });
        // req.session.user = payload;
    } catch (error) {
        res.redirect(state == 'login' ? '/login/google' : '/');
    }
};

module.exports = {
    loginGoogle,
    loginGoogleCallback,
}