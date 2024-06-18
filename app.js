require('dotenv').config();
const express = require('express');
const session = require('express-session');

const { loadModel } = require('./handler/inference');

const app = express();
const port = 3000;
const host = process.env.NODE_ENV !== 'production' ? 'localhost' : '0.0.0.0';

const auth = require('./routes/auth');
const predict = require('./routes/predict');
const status = require('./routes/status');
const history = require('./routes/history');

app.use(express.json());

app.use(session({
    secret: process.env.SESSION_SECRET,
    resave: false,
    saveUninitialized: true,
    cookie: {
        maxAge: 24 * 60 * 60 * 1000
    },
}));

app.use('/', auth);
app.use('/', predict);
app.use('/', status);
app.use('/', history);

loadModel();

app.listen(port, host, () => {
    console.log(`Server listening on port ${port}`);
});