import React, { useState } from "react";
import SearchResults from "../comp/SearchResults";
import SearchBar from "../comp/SearchBar";


const Search = () => {
  const [searchData, setSearchData] = useState(null);


  const handleSearchParams = (params) => {
    console.log("In search params:", params);
    setSearchData(params); 
  };


  return (
    <>

    <div className="base-container">
      <div className="parent">
        <div className="content-section">
          <div className="search-container-main"  >
            <SearchBar handleSearchParams={handleSearchParams} />
             </div>
          <div className="table_component">
            <SearchResults searchData={searchData} />

          </div>
        </div>
      </div>
    </div>
    </>
  );
};

export default Search;
