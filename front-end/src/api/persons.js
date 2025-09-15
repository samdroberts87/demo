// api/persons.js
import http from "./http"; // this import grabs the Axios library from the http file
// this also gives you access to the .get, .post, .create methods. 
// these methods allow me to make calls to the backend. 





// due to the inconsistant naming conventions i added this data mapping utility to 
// make sure the object and normalises it into a consistant format.
// p = {} is a default parameter this ensures that when nothing is passed it will not throw an error.
// I used this operator ?? which is a nullish operator which returns what is on the right if what is on the left is null
// I added this because when i was developing the code i was getting inconsistant API responses 
// Camelcase and SnakeCases
// This is a arrow fucntion attached to a variable and this variable cannot be reassigned because its a using the 'const' definition 
const mapPerson = (p = {}) => ({
  id: p.id ?? p.personId ?? null,
  firstName: (p.firstName ?? p.first_name ?? "").trim(),
  lastName: (p.lastName ?? p.last_name ?? "").trim(),
  dateOfBirth: p.dateOfBirth ?? p.date_of_birth ?? "",
  addressLine1: p.addressLine1 ?? p.address_line_1 ?? p.address ?? "",
  postcode: p.postcode ?? p.post_code ?? "",
  email: p.email ?? "",
});




// This section is where all the Get, Post, Create, Update calls are being made
// I have grouped all the api calls togther because i want to look in one place for all the API stuff
// it is cleaner and more efficient having everything in one place.


// GET all
export const getAllPersons = () =>
  http
    .get("/v1/person/get/all") // makes a GET request to the api endpoint which is pointing to the java code / tomcat server running using springboot
    .then((res) => // when the response arrives 
      (Array.isArray(res.data) // it checks if the response data is in a form of an array
       ? res.data.map(mapPerson) // is yes transform each item by using the mapPerson method (That i created above ^)
        : [])); // if it does not come back as an array it will return an empty array 

// similar concept happens to the other endpoints I have created

// GET by id
export const getPersonById = (id) => 
  http
    .get(`/v1/person/search/${id}`) // makes a get request to the API endpoint with the given ID
    .then((res) => // when it gets a response
       (res.data // then takes the response data 
         ? mapPerson(res.data) // if there is data it then applies the mapPerson function to the response (basically reformatting the data (remember the function above))
          : null));  // if there's no data it will return null 

// create
export const createPerson = (person) => 
  http.post("/v1/person/create", person) // makes a Post request to the API endpoint with person as the request body 
      .then((res) =>  // when the response comes through 
         mapPerson(res.data));  //uses the mapPerson metod to reformat the response 



export const updatePerson = (p) =>
  http
    .put("/v1/person/update/details", {  // makes a PUT Request to the API endpoint with p as the request body   
      // This is built from the object 'p' passed into Update person
      // and send it as JSON inside the HTTP request payload
      id: p.id,
      firstName: p.firstName,
      lastName: p.lastName,
      dateOfBirth: p.dateOfBirth, // "YYYY-MM-DD"
      addressLine1: p.addressLine1,
      postcode: p.postcode,
      email: p.email,
    })
    .then((res) => res.data); // when you get the response, return just the data


    // this endpoint points the the person and the ID i have attached ".delete" method to delete the data
export const deletePerson = (id) =>
  http.delete("/v1/person/delete", { params: { id } }).then(res => res.data);








    //Visas 
// Create a visa
export const createVisaForHolder = (visa) =>
  http.post("/v1/visa/create", visa).then((res) => res.data);

// Get all visas for a person
export const getVisasByPersonId = (personId) =>
  http.get(`/v1/visa/person/${personId}`).then((res) => res.data);

// Update a visa (id is a String — 16 chars)
export const updateVisa = (id, updates) =>
  http.put(`/v1/visa/update/details?id=${id}`, updates).then((res) => res.data);

// Delete a visa (id is a String — 16 chars)
export const deleteVisa = (id) =>
  http.delete(`/v1/visa/delete?id=${id}`).then((res) => res.data);











//Travel history end points

// CREATE travel history
export const createTravelHistory = (history) =>
  http.post("/v1/history/create", history).then((res) => res.data);

// GET all travel history for a person
export const getTravelHistoryByPersonId = (personId) =>
  http.get(`/v1/history/person/${personId}`).then((res) => res.data);

// UPDATE travel history record
export const updateTravelHistory = (id, updates) =>
  http
    .put(`/v1/history/update/details`, updates, { params: { id } })
    .then((res) => res.data);

// DELETE a travel history record
export const deleteTravelHistory = (id) =>
  http.delete(`/v1/history/delete`, { params: { id } })
     .then((res) => res.data);