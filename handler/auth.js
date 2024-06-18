require('dotenv').config();
const bcrypt = require('bcrypt');
const crypto = require('crypto');
const {
    storeUserData,
    checkUserExist,
    updateUserLastLogin,
    checkUserDataValid,
    getUserData } = require('../object/firestore');
const { createToken } = require('./token');

const register = async (req, res) => {
    const { name, email, password } = req.body;
    if (!name || !email || !password) {
        return res.status(400).json({
            status: 'failed',
            message: 'Please provide email & password',
        });
    }
    try {
        const userExist = await checkUserExist(email);
        if (userExist) {
            return res.status(400).json({
                status: 'failed',
                message: 'User already exists',
            });
        }

        const id = crypto.randomUUID();
        const hashedPassword = await bcrypt.hash(password, 10);
        // const createdAt = new Date().toISOString();
        const data = {
            id,
            name,
            email,
            password: hashedPassword,
        };
        try {
            storeUserData(id, data);
            updateUserLastLogin(id);
        } catch (error) {
            res.status(500).json({
                status: 'failed',
                message: 'Something is wrong when inserting data'
            });
        }
        res.status(201).json({
            status: 'success',
            message: 'User registered successfully'
        });
    } catch (error) {
        res.status(500).json({
            status: 'failed',
            message: 'Internal server error'
        });
    }
};

const login = async (req, res) => {
    const { email, password } = req.body;
    try {
        const { valid, id } = await checkUserDataValid(email, password);
        if (!valid) {
            return res.status(400).json({
                status: 'failed',
                message: 'Invalid email or password'
            });
        }
        updateUserLastLogin(id);
        const userData = await getUserData(id);
        const token = createToken(userData);
        res.json({
            status: 'success',
            message: 'Login success please use the token',
            token
        });
    } catch (error) {
        res.status(500).json({
            status: 'failed',
            message: 'Internal server error',
        });
    }
};

module.exports = {
    register,
    login,
}