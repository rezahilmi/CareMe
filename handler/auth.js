const bcrypt = require('bcrypt');
const crypto = require('crypto');
const { createToken } = require('./token');
const {
    storeUserData,
    checkUserExist,
    updateUserLastLogin,
    checkUserDataValid,
    getUserData, 
    getUserIdFromEmail
} = require('../object/firestore');

const register = async (req, res) => {
    const { name, email, password } = req.body;
    if (!name || !email || !password) {
        return res.status(400).json({
            status: 'failed',
            message: 'Please provide name, email, & password',
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
        const data = {
            id,
            name,
            email,
            password: hashedPassword,
        };
        try {
            await storeUserData(id, data);
            await updateUserLastLogin(id);
        } catch (error) {
            return res.status(500).json({
                status: 'failed',
                message: 'Something is wrong when inserting data'
            });
        }
        return res.status(201).json({
            status: 'success',
            message: 'User registered successfully',
            result: {
                id,
                name,
            }
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
        const valid = await checkUserDataValid(email, password);
        if (!valid) {
            return res.status(400).json({
                status: 'failed',
                message: 'Invalid email or password'
            });
        }
        const id = await getUserIdFromEmail(email);
        updateUserLastLogin(id);
        const userData = await getUserData(id);
        const token = createToken(userData);
        res.json({
            status: 'success',
            message: 'Login success please use the token',
            result: {
                token,
                id,
                name: userData.name,
            }
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