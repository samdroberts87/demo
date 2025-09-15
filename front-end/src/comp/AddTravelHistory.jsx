import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { createTravelHistory } from "../api/persons";

const AddTravelHistory = () => {
  useEffect(() => {
    document.title = `Add Travel History - Visa Holder Movement Tracker`;
  }, []);

  const { id: personId } = useParams(); // holder ID from URL
  const navigate = useNavigate();

  const [form, setForm] = useState({ country: "", entryDate: "", exitDate: "" });
  const [errors, setErrors] = useState({});
  const [saving, setSaving] = useState(false);

  const onChange = (field) => (e) =>
    setForm((prev) => ({ ...prev, [field]: e.target.value }));

  const onCancel = () => navigate(-1);

  // here is the data validation for the form
  const validate = (v) => {
    const e = {};
    if (!v.country?.trim()) e.country = "Country is required.";
    if (!v.entryDate) e.entryDate = "Entry date is required.";
    if (!v.exitDate) e.exitDate = "Exit date is required.";

    // Only compare when both are present
    if (v.entryDate && v.exitDate) {
      const entry = new Date(v.entryDate);
      const exit = new Date(v.exitDate);
      if (isNaN(entry.getTime())) e.entryDate = "Entry date is invalid.";
      if (isNaN(exit.getTime())) e.exitDate = "Exit date is invalid.";
      if (!e.entryDate && !e.exitDate && exit < entry) {
        e.exitDate = "Exit date cannot be before entry date.";
      }
    }
    return e;
  };

  const onSubmit = async () => {
    const trimmed = {
      country: form.country.trim(),
      entryDate: form.entryDate, // YYYY-MM-DD
      exitDate: form.exitDate,   // YYYY-MM-DD
    };

    // here is the data validation for the form
    const v = validate(trimmed);
    setErrors(v);
    if (Object.keys(v).length > 0) {
      alert("Please fix the highlighted fields.");
      return;
    }

    try {
      setSaving(true);
      await createTravelHistory({
        personId: Number(personId),
        ...trimmed,
      });
      alert("Travel history added successfully!");
      navigate(-1);
    } catch (e) {
      alert(`Failed to add travel history: ${e?.response?.data || e.message}`);
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="login-container addvisa-con">
      <div className="text-addVisa">
        <h2 className="login-txt addVisa">Add Travel History</h2>
      </div>

      <div className="login-usr addVisa">
        <label>Country</label>
        <input
          type="text"
          value={form.country}
          onChange={onChange("country")}
          placeholder="Country visited"
          required
          aria-invalid={!!errors.country}
          aria-describedby="err-country"
        />
        {errors.country && (
          <small id="err-country" className="error">
            {errors.country}
          </small>
        )}
      </div>

      <div className="section-add">
        <div className="login-password addVisa">
          <label>Entry Date</label>
          <input
            type="date"
            value={form.entryDate}
            onChange={onChange("entryDate")}
            required
            aria-invalid={!!errors.entryDate}
            aria-describedby="err-entry"
            // UX: prevent picking a date after the chosen exit date
            max={form.exitDate || undefined}
          />
          {errors.entryDate && (
            <small id="err-entry" className="error">
              {errors.entryDate}
            </small>
          )}
        </div>

        <div className="login-password addVisa">
          <label>Exit Date</label>
          <input
            type="date"
            value={form.exitDate}
            onChange={onChange("exitDate")}
            required
            aria-invalid={!!errors.exitDate}
            aria-describedby="err-exit"
            // UX: prevent picking a date before the chosen entry date
            min={form.entryDate || undefined}
          />
          {errors.exitDate && (
            <small id="err-exit" className="error">
              {errors.exitDate}
            </small>
          )}
        </div>
      </div>

      <div className="btn-container">
        <button className="btn-addVisa" onClick={onSubmit} disabled={saving}>
          {saving ? "Adding..." : "Add Travel History"}
        </button>
        <button className="btn-addVisa cancel" onClick={onCancel}>
          Cancel
        </button>
      </div>
    </div>
  );
};

export default AddTravelHistory;
