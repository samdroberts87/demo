import axios from "axios";


// this is better and cleaner, because would be to write axios.get() repeatedly you can just 
// write http.get() which does the same thing but simpler and cleaner syntax

// if I need to change the URL of the base syntax I can do it one place

// due to the nature of the project I wasnt't sure if we would incorperate authentication tokens we can use 
// such as interceptor which is a axios instance

 const http = axios.create({
    baseURL: import.meta.env.VITE_API_URL,
    headers: {"Content-Type": "application/json"}
})


export default http