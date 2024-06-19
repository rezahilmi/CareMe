const tfjs = require('@tensorflow/tfjs-node');

let model = null;

const loadModel = async () => {
    try {
        console.log('Loading model...');
        const url = process.env.NODE_ENV !== 'production' ? 'file://models/model.json' : process.env.MODEL_URL;
        model = await tfjs.loadGraphModel(url);
        console.log('Model loaded!');
        // return model;
    } catch (error) {
        console.log('Cannot load model!');
        // console.log(error);
        // return;
    }
}

const predictModel = async (image) => {
    if (!model) {
        return;
    }
    const tensor = tfjs.node
        .decodeImage(image, 3)
        .resizeBilinear([224, 224])
        .expandDims()
        .toFloat()
        .sub([123.68, 116.779, 103.939]);
    
    console.log((await tensor.toString()));
    const result = await model.predict(tensor);
    console.log(result);
    console.log(await result.data());
    return getPredictionData(await result.data());
}

const getPredictionData = (data) => {
    const classLabel = [
        'Warts Molluscum and other Viral Infections', 'Vasculitis Photos', 'Vascular Tumors', 'Urticaria Hives',
        'Tinea Ringworm Candidiasis and other Fungal Infections', 'Systemic Disease', 'Seborrheic Keratoses and other Benign Tumors',
        'Scabies Lyme Disease and other Infestations and Bites', 'Psoriasis pictures Lichen Planus and related diseases',
        'Poison Ivy Photos and other Contact Dermatitis', 'Nail Fungus and other Nail Disease', 'Melanoma Skin Cancer Nevi and Moles',
        'Lupus and other Connective Tissue diseases', 'Light Diseases and Disorders of Pigmentation', 'Herpes HPV and other STDs Photos',
        'Hair Loss Photos Alopecia and other Hair Diseases', 'Exanthems and Drug Eruptions', 'Eczema Photos',
        'Cellulitis Impetigo and other Bacterial Infections', 'Bullous Disease Photos', 'Atopic Dermatitis Photos',
        'Actinic Keratosis Basal Cell Carcinoma and other Malignant Lesions', 'Acne and Rosacea Photos'
    ];
    const descriptionLabel = [];
    const recommendationLabel = [];

    const index = data.indexOf(Math.max(...data));
    const predictionResult = classLabel[index];
    const description = null;
    const recommendation = null;

    const predictionData = {
        predictionResult: predictionResult || 'Unknown',
        description: description || 'No description available',
        recommendation: recommendation || 'No recommendation avaiable',
    };
    return predictionData;
}

// loadModel();

module.exports = {
    model,
    loadModel,
    predictModel,
    getPredictionData,
}