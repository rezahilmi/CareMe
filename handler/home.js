const getInfo = (req, res) => {
    return res.json({
        status: 'success',
        message: 'Welcome to CareMe API',
        endpoints: {
            register: {
                url: '/register',
                method: 'POST',
                requestBody: {
                    name: 'string',
                    email: 'string',
                    password: 'string',
                },
            },
            login: {
                url: '/login',
                method: 'POST',
                requestBody: {
                    email: 'string',
                    password: 'string',
                },
            },
            predict: {
                url: '/predict',
                method: 'POST',
                headers: {
                    contentType: 'multipart/form-data',
                    authorization: 'Bearer <token>',
                },
                requestBody: {
                    image: 'image',
                },
            },
            history: {
                url: '/history',
                method: 'GET',
                headers: {
                    authorization: 'Bearer <token>',
                },
            },
            specificHistory: {
                url: '/history/:historyId',
                method: 'GET',
                headers: {
                    authorization: 'Bearer <token>',
                },
            },
        }
    });
};

module.exports = { getInfo }