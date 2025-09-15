import "./App.css";
import "./search.css";
import "./login.css";
import "./addVisa.css";
import "./visaHolderDet.css";
import "./navbar.css";

import { Routes, Route, useLocation } from "react-router-dom";

import Navbar from "./comp/Navbar";
import Alert from "./pages/Alert";
import AddVisa from "./pages/AddVisa";
import LoginPage from "./pages/LoginPage";
import Search from "./pages/Search";
import VisaHolderDetails from "./comp/VisaHolderDetails";
import EditVisa from "./comp/EditVisa";
import AddVisaToHolder from "./comp/AddVisaToHolder";
import AddTravelHistory from "./comp/AddTravelHistory";

function App() {
  const location = useLocation();
  const hideNavbar = location.pathname === "/"; // hide on login

  return (
    <div className="app-wrapper">
      {!hideNavbar && <Navbar />}

      <div className="main-content">
        <Routes>
          <Route path="/" element={<LoginPage />} />
          <Route path="/search" element={<Search />} />

          <Route path="/addVisa" element={<AddVisa />} />

          <Route path="/alert" element={<Alert />} />

          <Route path="/addVisaToHolder/:id" element={<AddVisaToHolder />} />

          <Route
            path="/visaHolderDetails/:id"
            element={<VisaHolderDetails />}
          />

          <Route path="/editVisa/:id" element={<EditVisa />} />


          <Route path="/addTravelHistory/:id" element={<AddTravelHistory />} />
        </Routes>
      </div>
    </div>
  );
}

export default App;
