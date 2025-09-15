import React, { useState } from "react";
import http from "../api/http";


const SearchBar = ({ handleSearchParams }) => {
  const [params, setParams] = useState({
    id: "",
    firstName: "",
    lastName: "",
    dateOfBirth: "",
    addressLine1: "",
    postcode: "",
    email: "",
  });

  const handleChange = (field, value) => {
    setParams((prev) => ({ ...prev, [field]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // Drop empty/whitespace-only fields
    const cleaned = Object.fromEntries(
      Object.entries(params).filter(
        ([, v]) => v != null && String(v).trim() !== ""
      )
    );

    // Send only non-empty filters to parent
    handleSearchParams(cleaned);
  };

  return (
    <form onSubmit={handleSubmit} className="search-bar">
      <div className="search-container-one">
        <input
          type="text"
          name="id"
          placeholder="ID"
          value={params.id}
          onChange={(e) => handleChange("id", e.target.value)}
        />
        <input
          name="firstName"
          type="text"
          placeholder="First Name"
          value={params.firstName}
          onChange={(e) => handleChange("firstName", e.target.value)}
        />
        <input
          name="lastName"
          type="text"
          placeholder="Last Name"
          value={params.lastName}
          onChange={(e) => handleChange("lastName", e.target.value)}
        />
        <input
          name="dateOfBirth"
          type="date"
          placeholder="Date of Birth"
          value={params.dateOfBirth}
          onChange={(e) => handleChange("dateOfBirth", e.target.value)}
        />
      </div>

      <div className="search-container-two">
        <input
          name="addressLine1"
          type="text"
          placeholder="Address Line 1"
          value={params.addressLine1}
          onChange={(e) => handleChange("addressLine1", e.target.value)}
        />
        <input
          name="postcode"
          type="text"
          placeholder="Postcode"
          value={params.postcode}
          onChange={(e) => handleChange("postcode", e.target.value)}
        />
        <input
          name="email"
          type="email"
          placeholder="Email"
          value={params.email}
          onChange={(e) => handleChange("email", e.target.value)}
        />
      </div>

      <button type="submit">Search</button>
    </form>
  );
};

export default SearchBar;
