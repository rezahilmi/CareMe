require('dotenv').config();
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const crypto = require('crypto');
const { storeData, checkUserExist, checkUserInfoValid } = require('../object/firestore');
const { verifyToken } = require('./verify');

const register = async (req, res) => {
    const { email, password } = req.body;
    try {
        const userExist = await checkUserExist(email);
        if (userExist) {
            return res.status(400).json({
                status: 'failed',
                message: 'User already exists'
            });
        }

        const id = crypto.randomUUID();
        const hashedPassword = await bcrypt.hash(password, 10);

        const createdAt = new Date().toISOString();
        const data = {
            id,
            email,
            password: hashedPassword,
            createdAt,
            lastLogin: createdAt,
        };
        try {
            await storeData(id, data);
        } catch (error) {
            res.status(500).json({
                status: 'failed',
                message: 'Something wrong when inserting data'
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
        const valid = await checkUserInfoValid(email, password);
        if (!valid) {
            return res.status(400).json({
                status: 'failed',
                message: 'Invalid email or password'
            });
        }

        const token = jwt.sign({
            uid: userSnapshot.docs[0].id,
            email: user.email
        }, process.env.JWT_SECRET, { expiresIn: '7d' });

        res.json({ token });
    } catch (error) {
        res.status(500).json({
            status: 'failed',
            message: 'Internal server error'
        });
    }
};

module.exports = {
    register,
    login,
}