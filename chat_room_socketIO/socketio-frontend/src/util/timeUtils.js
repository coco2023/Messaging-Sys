export const timeStampConverter = (time) => {
    const date = new Date(time);
    const hour = date.getHours();
    const minute = date.getMinutes();
    // return `${date}:${hour}:${minute}`
    return `${hour}:${minute}`;
};