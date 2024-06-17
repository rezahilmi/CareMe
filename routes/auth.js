const express = require('express');
const oauth2Client = require('../controller/oauth');
const firestore = require('../controller/firestore');

const router = express.Router();

router.get('/login', (req, res) => {
    const url = oauth2Client.generateAuthUrl({
        access_type: 'offline',
        scope: [
            'https://www.googleapis.com/auth/userinfo.profile',
            'https://www.googleapis.com/auth/userinfo.email'
        ],
        state: 'login',
    });
    res.redirect(url);
});

router.get('/login/google/callback', async (req, res) => {
    const { code, state, error } = req.query;

    if (error) {
        return res.redirect(state == 'login' ? '/login' : '/');
    }

    try {
        const { tokens } = await oauth2Client.getToken(code);
        oauth2Client.setCredentials(tokens);
        console.log(tokens);

        const ticket = await oauth2Client.verifyIdToken({
            idToken: tokens.id_token,
            audience: process.env.CLIENT_ID,
        });
        const payload = ticket.getPayload();
        console.log(payload);

        const userRef = firestore.collection('users').doc(payload.sub);
        await userRef.set({
            id: payload.sub,
            displayName: payload.name,
            email: payload.email,
            picture: payload.picture,
            provider: 'google',
            lastLogin: new Date().toISOString()
        }, { merge: true });

        req.session.user = payload;
        res.redirect('/');
    } catch (error) {
        res.redirect(state == 'login' ? '/login' : '/');
    }

});

router.get('/', (req, res) => {
    if (req.session.user) {
        res.send({
            result: 'success',
            data: req.session.user,
        });
    } else {
        res.send('Goto /login to login');
    }
});

router.get('/logout', (req, res) => {
    req.session.destroy((err) => {
        if (err) {
            return res.status(500).send('Failed to logout');
        }
        res.redirect('/');
    });
});

module.exports = router;
