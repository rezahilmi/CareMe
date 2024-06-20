const oauth2Client = require('../object/oauth');
const { createToken } = require('./token');
const { updateUserLastLogin, getUserData, storeUserData } = require('../object/firestore');

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
        
        const userData = await getUserData(payload.sub);
        const token = createToken(userData);
        res.json({
            status: 'success',
            message: 'Login success please use the token',
            result: {
                token
            }
        });
    } catch (error) {
        res.redirect(state == 'login' ? '/login/google' : '/');
    }
};

module.exports = {
    loginGoogle,
    loginGoogleCallback,
}