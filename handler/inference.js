const tfjs = require('@tensorflow/tfjs-node');

let model = null;

const loadModel = async () => {
    try {
        console.log('Loading model...');
        // const url = process.env.NODE_ENV !== 'production' ? 'file://models/model.json' : process.env.MODEL_URL;
        const url = process.env.MODEL_URL;
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
    
    console.log(tensor.toString());
    const result = await model.predict(tensor).data();
    console.log(result);
    return getPredictionData(result);
}

const getPredictionData = (data) => {
    const classLabel = [
        'Warts Molluscum and other Viral Infections',
        'Vasculitis Photos',
        'Vascular Tumors',
        'Urticaria Hives',
        'Tinea Ringworm Candidiasis and other Fungal Infections',
        'Systemic Disease',
        'Seborrheic Keratoses and other Benign Tumors',
        'Scabies Lyme Disease and other Infestations and Bites',
        'Psoriasis pictures Lichen Planus and related diseases',
        'Poison Ivy Photos and other Contact Dermatitis',
        'Nail Fungus and other Nail Disease',
        'Melanoma Skin Cancer Nevi and Moles',
        'Lupus and other Connective Tissue diseases',
        'Light Diseases and Disorders of Pigmentation',
        'Herpes HPV and other STDs Photos',
        'Hair Loss Photos Alopecia and other Hair Diseases',
        'Exanthems and Drug Eruptions', 'Eczema Photos',
        'Cellulitis Impetigo and other Bacterial Infections',
        'Bullous Disease Photos',
        'Atopic Dermatitis Photos',
        'Actinic Keratosis Basal Cell Carcinoma and other Malignant Lesions',
        'Acne and Rosacea Photos',
    ];
    const descriptionLabel = [
        'Molluscum contagiosum and warts are benign epidermal eruptions resulting from viral infections of the skin. Molluscum contagiosum eruptions are usually self-limited and without sequelae, although they can be more extensive in immunocompromised persons.',
        'Vasculitis involves inflammation of the blood vessels. The inflammation can cause the walls of the blood vessels to thicken, which reduces the width of the passageway through the vessel. If blood flow is restricted, it can result in organ and tissue damage.',
        'Vascular tumors are a large and complex group of lesions, especially for clinicians with none or little experience in this field.',
        'Hives, also called urticaria, is a skin reaction that causes itchy welts. Chronic hives are welts that last for more than six weeks and return often over months or years.',
        'Fungal infections, or mycosis, are diseases caused by a fungus (yeast or mold). Fungal infections are most common on your skin or nails, but fungi (plural of fungus) can also cause infections in your mouth, throat, lungs, urinary tract and many other parts of your body.',
        'Systemic disorders are health conditions affecting one or more of the body\'s systems, including but not limited to the respiratory immunological, neurological, circulatory, or digestive systems.',
        'Seborrheic keratosis is a common, benign, acquired skin lesion that occurs on the sun-exposed skin of the face and trunk of middle-aged and older adults.',
        'Scabies is an Infestation of the skin by the human itch mite, Sarcoptes scabiei. The initial symptom of scabies are red, raised bumps that are intensely itchy. A magnifying glass will reveal short, wavy lines of red skin, which are the burrows made by the mites.',
        'Lichen planus is a common inflammatory skin condition. It is not contagious and not a sign of cancer. It appears as firm, shiny, reddish bumps. Psoriasis is the most common autoimmune condition in the United States. It is also not contagious or a sign of cancer.',
        'Poison ivy rash is caused by contact with poison ivy, a plant that grows almost everywhere in the United States. The sap of the poison ivy plant, also known as Toxicodendron radicans, contains an oil called urushiol. This is the irritant that causes an allergic reaction and rash.',
        'Nail fungus is a common infection of the nail. It begins as a white or yellow-brown spot under the tip of your fingernail or toenail. As the fungal infection goes deeper, the nail may discolor, thicken and crumble at the edge.',
        'Dysplastic nevus is a mole that looks different from most moles. The mole may have irregular borders, be a mix of colors and appear larger than other moles. Atypical moles are benign (not cancerous).',
        'Lupus is a chronic autoimmune disease where the immune system is dysfunctional and mistakenly identifies the body\'s own tissues as foreign invaders.',
        'Pigmentation means coloring. Skin pigmentation disorders affect the color of your skin. Your skin gets its color from a pigment called melanin. Special cells in the skin make melanin. When these cells become damaged or unhealthy, it affects melanin production. Some pigmentation disorders affect just patches of skin. Others affect your entire body.',
        'Herpes and HPV are both viruses that usually affect the skin and both have types that can be passed on through sexual contact.',
        'Alopecia areata is a disease that happens when the immune system attacks hair follicles and causes hair loss.',
        'Exanthematous (maculopapular) drug eruptions usually begin 4 to 21 days after the responsible drug is started and rapidly evolve into widespread rash.',
        'Eczema is a skin condition that causes dry and itchy patches of skin. It\'s a common condition that isn\'t contagious. Symptoms of eczema can flare up if you contact an irritant or an allergen.',
        'Impetigo is a highly contagious skin infection that leaves red sores on the face and spreads by skin-to-skin contact. Cellulitis affects your skin and the soft tissue below it. It arises when your skin has a crack or break, allowing bacteria (most commonly streptococcus and staphylococcus) to enter and spread quickly.',
        'Bullous diseases are a group of skin disorders in which the primary lesion is a vesicle or bulla.',
        'Atopic dermatitis, often referred to as eczema, is a chronic (long-lasting) disease that causes inflammation, redness, and irritation of the skin. It is a common condition that usually begins in childhood; however, anyone can get the disease at any age. Atopic dermatitis is not contagious, so it cannot be spread from person to person.',
        'Actinic keratosis is responsible for more than eight million visits to dermatologists and primary care physicians annually. Actinic keratosis, the result of chronic sun damage to the skin, is closely linked to nonmelanoma skin cancer, both histologically and pathophysiologically.',
        'Acne is a common skin condition that happens when hair follicles under the skin become clogged. Sebum—oil that helps keep skin from drying out—and dead skin cells plug the pores, which leads to outbreaks of lesions, commonly called pimples or zits. Most often, the outbreaks occur on the face but can also appear on the back, chest, and shoulders. Rosacea is a long-term inflammatory skin condition that causes reddened skin and a rash, usually on the nose and cheeks. It may also cause eye problems. The symptoms typically come and go, with many people reporting that certain factors, such as spending time in the sun or experiencing emotional stress, bring them on.',
    ];
    const recommendationLabel = [
        ['Salicylic Acid', 'Cryotherapy', 'Imiquimod', 'Cantharidin (for molluscum)'],
        ['Corticosteroids', 'Immunosuppressants (Cyclophosphamide, Azathioprine)'],
        ['Propranolol (for infantile hemangiomas)', 'Corticosteroids'],
        ['Antihistamines (Cetirizine, Loratadine)', 'Corticosteroids (for severe cases)'],
        ['Topical Antifungals (Clotrimazole, Miconazole)', 'Oral Antifungals (Terbinafine, Fluconazole)'],
        ['Systemic Corticosteroids', 'Immunosuppressants'],
        ['Cryotherapy', 'Curettage'],
        ['Permethrin Cream (for scabies)', 'Ivermectin (oral for scabies)', 'Antibiotics (Doxycycline for Lyme disease)'],
        ['Topical Corticosteroids', 'Vitamin D Analogues (Calcipotriol)', 'Retinoids (Tazarotene)', 'Biologics (Adalimumab, Ustekinumab)'],
        ['Topical Corticosteroids', 'Oral Antihistamines', 'Calamine Lotion'],
        ['Antifungals (Terbinafine, Itraconazole, Ciclopirox)'],
        ['Surgical excision', 'Topical Imiquimod (for superficial basal cell carcinoma)', 'Chemotherapy (for advanced melanoma)', 'Immune checkpoint inhibitors (Pembrolizumab, Nivolumab)'],
        ['Corticosteroids', 'Hydroxychloroquine', 'Immunosuppressants (Methotrexate, Azathioprine)'],
        ['Sunscreens (broad-spectrum SPF 30 or higher)', 'Hydroquinone (for hyperpigmentation)', 'Tretinoin', 'Corticosteroids (for vitiligo)'],
        ['Antivirals (Acyclovir, Valacyclovir, Famciclovir for herpes)', 'Imiquimod (for HPV warts)', 'Podofilox (for genital warts)'],
        ['Minoxidil', 'Finasteride', 'Corticosteroids (for alopecia areata)', 'Platelet-rich plasma (PRP) therapy'],
        ['Corticosteroids (for inflammation)', 'Antihistamines (for itching)', 'Discontinuation of the causative drug'],
        ['Moisturizers', 'Topical Corticosteroids', 'Calcineurin Inhibitors', 'Antihistamines'],
        ['Antibiotics (Penicillin, Cephalexin, Clindamycin, Amoxicillin-Clavulanate)'],
        ['Corticosteroids (topical and systemic)', 'Immunosuppressants (Azathioprine, Mycophenolate Mofetil)', 'Antibiotics (for secondary infections)'],
        ['Topical Corticosteroids', 'Calcineurin Inhibitors (Tacrolimus, Pimecrolimus)', 'Moisturizers (with ceramides, glycerin)', 'Antihistamines (for itching)', 'Dupilumab (biologic for severe cases)'],
        ['Fluorouracil (5-FU) cream', 'Imiquimod', 'Diclofenac Gel', 'Ingenol Mebutate'],
        ['Benzoyl Peroxide (for acne)', 'Salicylic Acid (for acne)', 'Retinoids (Tretinoin, Adapalene) (for acne)', 'Antibiotics (Clindamycin, Erythromycin) (for acne)', 'Niacinamide  (for rosacea)', 'Metronidazole  (for rosacea)', 'Ivermectin  (for rosacea)', 'Brimonidine Gel  (for rosacea)', 'Oral Antibiotic (Doxycycline) (for rosacea)', 'Azelaic Acid'],
    ];

    const index = data.indexOf(Math.max(...data));
    const predictionResult = classLabel[index];
    const description = descriptionLabel[index];
    const recommendation = recommendationLabel[index];

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