require('dotenv').config();
const express = require('express');
const session = require('express-session');
const app = express();
const port = 3000;
const host = process.env.NODE_ENV !== 'production' ? 'localhost' : '0.0.0.0';

const testRoute = require('./routes/auth');

app.use(session({
    secret: process.env.SESSION_SECRET,
    resave: false,
    saveUninitialized: true,
}));

app.use('/', testRoute);

app.listen(port, host, () => {
    console.log(`Example app listening on port ${port}`);
});