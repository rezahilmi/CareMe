const { predict } = require('./inference');
const crypto = require('crypto');

const getStatus = (request, h) => {
    if (!request.auth.isAuthenticated) {
        return h.response({
            status: 'API working',
        }).code(200);
    }
    const credentials = request.auth.credentials
    return h.response({
        credentials,
    }).code(200);
}

const authHandler = (request, h) => {
    if (!request.auth.isAuthenticated) {
        return `Authentication failed due to: ${request.auth.error.message}`;
    }
    return h.redirect('/');
}

const postPredictHandler = async (request, h) => {
    // const { image } = request.payload;
    // const { model } = request.server.app;

    // const { suggestion } = await predict(model, image);
    // const id = crypto.randomUUID();
    // const createdAt = new Date().toISOString();

    const data = {
        id,
        suggestion,
        createdAt,
    }

    return h.response({
        status: 'success',
        message: 'Model is predicted successfully',
        data
    }).code(201);
}

const postCheckFailHandler = (request, h) => {
    const response = request.response;

    if (response.isBoom && response.output.statusCode == 500) {
        return h.response({
            status: 'fail',
            message: 'Terjadi kesalahan dalam melakukan prediksi',
        }).code(400);
    }

    if (response.isBoom && response.output.statusCode == 413) {
        return h.response({
            status: 'fail',
            message: 'Payload content length greater than maximum allowed: 1000000',
        }).code(413);
    }

    return h.continue;
};

module.exports = { postPredictHandler, postCheckFailHandler, getStatus, authHandler };