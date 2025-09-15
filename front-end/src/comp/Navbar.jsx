import React from "react";
import { Link } from "react-router-dom";
import { IoMdPersonAdd } from "react-icons/io";
import { MdOutlineDashboard } from "react-icons/md";
import { MdOutlineReportGmailerrorred } from "react-icons/md";
import { GrLogout } from "react-icons/gr";
import { MdTravelExplore } from "react-icons/md";
import { FaSearch } from "react-icons/fa";

const Navbar = () => {
  return (
    <div className="navbar">
      <ul>
        <Link to="/search">
          <li>
            <FaSearch />
            Search
          </li>
        </Link>
        <Link to="/addVisa">
          <li>
            <IoMdPersonAdd />
            Add Visa
          </li>
        </Link>
        <Link to="/alerts">
          <li>
            <MdOutlineReportGmailerrorred />
            Report
          </li>
        </Link>

        <Link to="/">
          <li>
            <GrLogout />
            LogOut
          </li>
        </Link>

      </ul>
    </div>
  );
};

export default Navbar;
