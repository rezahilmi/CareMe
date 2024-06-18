

const predict = (req, res) => {
    
    return res.json({
        status: 'success',
        message: 'Predict success',
        data: 'This is a placeholder'
    });
}

module.exports = { predict }