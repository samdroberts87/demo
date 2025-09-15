import React, { useEffect, useState } from "react";
import { useLocation, useParams, useNavigate } from "react-router-dom";
import { getPersonById, getVisasByPersonId } from "../api/persons";
import { getTravelHistoryByPersonId } from "../api/persons";
import { deleteTravelHistory } from "../api/persons";
import { updateTravelHistory } from "../api/persons";
import { deleteVisa } from "../api/persons";






const VisaHolderDetails = () => {

      useEffect(() => {
        document.title = `Visa Holder Details - Visa Holder Movement Tracker`;
        }, []);





  const { id } = useParams();
  const location = useLocation();
  const navigate = useNavigate();

  const [person, setPerson] = useState(location.state?.person || null);
  const [visas, setVisas] = useState([]);
  const [travelHistory, setTravelHistory] = useState([]);
  const [loading, setLoading] = useState(true);
  const [err, setErr] = useState(null);

  useEffect(() => {
    const loadData = async () => {
      try {
        // 1) ensure we have the person
        let p = person;
        if (!p) {
          p = await getPersonById(id);
          setPerson(p);
        }

        // 2) fetch visas
        const list = await getVisasByPersonId(p.id);
        setVisas(Array.isArray(list) ? list : []);

        // 3) fetch travel history
        const history = await getTravelHistoryByPersonId(p.id);
        setTravelHistory(Array.isArray(history) ? history : []);
      } catch (e) {
        setErr(e.message || "Failed to load data");
      } finally {
        setLoading(false);
      }
    };

    loadData();
  }, [id]); // re-run if id changes

  // Visa edit and Remove fucntions
// Edit (prompt-based, like your travel history edit)
// Edit (prompt-based, like your travel history edit)
const handleEditVisa = async (record) => {
  const newCountry = prompt("Enter new country", record.country ?? "") ?? "";
  const newType    = prompt("Enter visa type", record.visaType ?? "") ?? "";
  const newStart   = prompt("Enter start date (YYYY-MM-DD)", record.visaStartDate ?? "") ?? "";
  const newExpiry  = prompt("Enter expiry date (YYYY-MM-DD)", record.visaExpiryDate ?? "") ?? "";

  const country = newCountry.trim();
  const visaType = newType.trim();
  const visaStartDate = newStart.trim();
  const visaExpiryDate = newExpiry.trim();

  if (!country || !visaType || !visaStartDate || !visaExpiryDate) {
    alert("All fields are required.");
    return;
  }
  const iso = /^\d{4}-\d{2}-\d{2}$/;
  if (!iso.test(visaStartDate) || !iso.test(visaExpiryDate)) {
    alert("Dates must be in YYYY-MM-DD format.");
    return;
  }
  if (visaStartDate > visaExpiryDate) {
    alert("Start date cannot be after expiry date.");
    return;
  }

  const updates = {};
  if (country !== record.country) updates.country = country;
  if (visaType !== record.visaType) updates.visaType = visaType;
  if (visaStartDate !== record.visaStartDate) updates.visaStartDate = visaStartDate;
  if (visaExpiryDate !== record.visaExpiryDate) updates.visaExpiryDate = visaExpiryDate;
  if (Object.keys(updates).length === 0) return;

  try {
    await updateVisa(record.id, updates);
    setVisas((prev) => prev.map((v) => (v.id === record.id ? { ...v, ...updates } : v)));
  } catch (e) {
    alert("Failed to update visa: " + (e?.response?.data || e.message));
  }
};

const handleRemoveVisa = async (visaId) => {
  if (!window.confirm("Are you sure you want to delete this visa?")) return;
  try {
    await deleteVisa(visaId);
    setVisas((prev) => prev.filter((v) => v.id !== visaId));
  } catch (e) {
    alert("Failed to delete visa: " + (e?.response?.data || e.message));
  }
};
  // End

  // Travel Edit and Remove functions
  const handleRemoveTravel = async (historyId) => {
    if (!window.confirm("Are you sure you want to delete this record?")) return;
    try {
      await deleteTravelHistory(historyId);
      setTravelHistory((prev) => prev.filter((t) => t.id !== historyId));
    } catch (e) {
      alert(
        "Failed to delete travel history: " + (e.response?.data || e.message)
      );
    }
  };

  const handleEditTravel = async (record) => {
    // 1) Collect new values (prefill with current)
    const newCountry = prompt("Enter new country", record.country ?? "") ?? "";
    const newEntry =
      prompt(
        "Enter entry date (YYYY-MM-DD)",
        record.entryDate ?? record.entry_date ?? ""
      ) ?? "";
    const newExit =
      prompt(
        "Enter exit date (YYYY-MM-DD)",
        record.exitDate ?? record.exit_date ?? ""
      ) ?? "";

    // 2) Trim + basic validation
    const country = newCountry.trim();
    const entryDate = newEntry.trim();
    const exitDate = newExit.trim();

    if (!country || !entryDate || !exitDate) {
      alert("Country, Entry Date, and Exit Date are required.");
      return;
    }
    // very light YYYY-MM-DD check
    const isoRe = /^\d{4}-\d{2}-\d{2}$/;
    if (!isoRe.test(entryDate) || !isoRe.test(exitDate)) {
      alert("Dates must be in YYYY-MM-DD format.");
      return;
    }
    if (entryDate > exitDate) {
      alert("Entry date cannot be after exit date.");
      return;
    }

    // 3) Build updates object with only changed fields
    const updates = {};
    if (country !== (record.country ?? "")) updates.country = country;
    const oldEntry = record.entryDate ?? record.entry_date ?? "";
    const oldExit = record.exitDate ?? record.exit_date ?? "";
    if (entryDate !== oldEntry) updates.entryDate = entryDate;
    if (exitDate !== oldExit) updates.exitDate = exitDate;

    if (Object.keys(updates).length === 0) {
      // Nothing changed
      return;
    }

    // 4) Call API and update local state
    try {
      await updateTravelHistory(record.id, updates);
      setTravelHistory((prev) =>
        prev.map((t) => (t.id === record.id ? { ...t, ...updates } : t))
      );
    } catch (e) {
      alert(
        "Failed to update travel history: " + (e?.response?.data || e.message)
      );
    }
  };

  // End of Travel and Remove functions

  if (loading) return <div>Loading...</div>;
  if (err) return <div>Error: {err}</div>;
  if (!person) return <div>No data</div>;

  return (
    <div className="container-visa-holder-deets">
      {/* Buttons */}

      {/* Person Info */}
      <h1 className="head-details">
        {person.firstName} {person.lastName}
      </h1>

      {/* Visa Table */}
      <h2>Visas</h2>
      <table className="table-details">
        <thead>
          <tr>
            <th>Visa ID</th>
            <th>Country</th>
            <th>Type</th>
            <th>Start Date</th>
            <th>Expiry Date</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {visas.length === 0 ? (
            <tr>
              <td colSpan="6">No visas found</td>
            </tr>
          ) : (
            visas.map((v) => (
              <tr key={v.id}>
                <td>{v.id}</td>
                <td>{v.country}</td>
                <td>{v.visaType}</td>
                <td>{v.visaStartDate}</td>
                <td>{v.visaExpiryDate}</td>
                <td>
                  <button className="edit-btn" onClick={() => handleEditVisa(v)}>Edit</button>
                  <button className="remove-btn" onClick={() => handleRemoveVisa(v.id)}>
                    Remove
                  </button>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>

      {/* Travel History */}
      <h2>Travel History</h2>
      <table className="table-details">
        <thead>
          <tr>
            <th>Travel ID</th>
            <th>Country Visited</th>
            <th>Entry Date</th>
            <th>Exit Date</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {travelHistory.length === 0 ? (
            <tr>
              <td colSpan="6">No travel history found</td>
            </tr>
          ) : (
            travelHistory.map((t) => (
              <tr key={t.id}>
                <td>{t.id}</td>
                <td>{t.country}</td>
                <td>{t.entryDate}</td>
                <td>{t.exitDate}</td>
                <td>
                  <button onClick={() => handleEditTravel(t)}>Edit</button>
                  <button onClick={() => handleRemoveTravel(t.id)}>
                    Remove
                  </button>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>



      

      <div className="btn-container">
        <button
          className="btn-addVisa"
          onClick={() =>
            navigate(`/editVisa/${person.id}`, { state: { person } })
          }
        >
          Edit
        </button>
        <button
          className="btn-addVisa"
          onClick={() =>
            navigate(`/addTravelHistory/${person.id}`, { state: { person } })
          }
        >
          Add Travel history
        </button>
        <button
          className="btn-addVisa"
          onClick={() =>
            navigate(`/addVisaToHolder/${person.id}`, { state: { person } })
          }
        >
          Add Visa
        </button>
        <button className="btn-addVisa cancel" onClick={() => navigate(-1)}>
          Cancel
        </button>
      </div>
    </div>
  );
};

export default VisaHolderDetails;
