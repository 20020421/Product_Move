export const getFormData = (object) => {
    console.log(object)
    const formData = new FormData();
    Object.keys(object).forEach(key => formData.append(key, object[key]));
    console.log(formData)
    return formData;
}