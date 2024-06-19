require('dotenv').config();
const { OAuth2Client } = require('google-auth-library');

const oauth2Client = new OAuth2Client(
    process.env.CLIENT_ID,
    process.env.CLIENT_SECRET,
    process.env.REDIRECT_URL || 'http://localhost:3000/login/google/callback',
);

module.exports = oauth2Client;