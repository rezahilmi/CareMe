const tfjs = require('@tensorflow/tfjs-node');

const loadModel = async () => {
    try {
        console.log('Loading model...');
        const url = process.env.NODE_ENV !== 'production' ? 'file://models/model.json' : process.env.MODEL_URL;
        const model = await tfjs.loadLayersModel(url);
        console.log('Model loaded!');
        return model;
    } catch (error) {
        console.log('Cannot load model!');
        console.log(error);
        return;
    }
}

// loadModel();

module.exports = {
    loadModel
}