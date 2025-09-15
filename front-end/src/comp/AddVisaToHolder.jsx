// src/pages/AddVisaToHolder.jsx
import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { FiUpload } from "react-icons/fi";
import { createVisaForHolder } from "../api/persons";

// These are the Visa Types allowed by the backend
const VISA_TYPES = ["Business", "Work", "Transit", "Family", "Study", "Visit"];

// Empty template for the form
const emptyVisa = {
  id: "",
  country: "",
  visaType: "",
  visaStartDate: "",
  visaExpiryDate: "",
};

const AddVisaToHolder = () => {
  useEffect(() => {
    document.title = `Add Visa To Holder - Visa Holder Movement Tracker`;
  }, []);

  const { id: personId } = useParams(); // holder id from URL
  const navigate = useNavigate();

  const [form, setForm] = useState(emptyVisa);
  const [errors, setErrors] = useState({});
  const [saving, setSaving] = useState(false);

  const onChange = (field) => (e) =>
    setForm((prev) => ({ ...prev, [field]: e.target.value }));

  const onCancel = () => navigate(-1);

  // here is the data validation for the form
  const validate = (v) => {
    const e = {};
    const id16 = (v.id || "").trim();

    if (!id16) e.id = "Visa ID is required.";
    else if (id16.length !== 16) e.id = "Visa ID must be exactly 16 characters.";

    if (!v.country?.trim()) e.country = "Country is required.";

    if (!v.visaType) e.visaType = "Visa type is required.";
    else if (!VISA_TYPES.includes(v.visaType))
      e.visaType = "Visa type must be one of the allowed options.";

    if (!v.visaStartDate) e.visaStartDate = "Start date is required.";
    if (!v.visaExpiryDate) e.visaExpiryDate = "Expiry date is required.";

    // Only compare dates if both present
    if (v.visaStartDate && v.visaExpiryDate) {
      const start = new Date(v.visaStartDate);
      const expiry = new Date(v.visaExpiryDate);
      if (isNaN(start.getTime())) e.visaStartDate = "Start date is invalid.";
      if (isNaN(expiry.getTime())) e.visaExpiryDate = "Expiry date is invalid.";
      if (!e.visaStartDate && !e.visaExpiryDate && expiry < start) {
        e.visaExpiryDate = "Expiry date cannot be before start date.";
      }
    }

    return e;
  };

  const onSubmit = async () => {
    const trimmed = {
      id: (form.id || "").trim(),
      country: (form.country || "").trim(),
      visaType: form.visaType,
      visaStartDate: form.visaStartDate,
      visaExpiryDate: form.visaExpiryDate,
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
      const payload = {
        ...trimmed,
        personId: Number(personId), // holder ID
      };

      await createVisaForHolder(payload);
      alert("Visa added successfully!");
      navigate(-1);
    } catch (e) {
      console.error("Failed to add visa:", e?.response?.data || e.message);
      alert(`Failed to add visa: ${e?.response?.data || e.message}`);
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="login-container addvisa-con">
      <div className="text-addVisa">
        <h2 className="login-txt addVisa">Add Visa to Holder</h2>
      </div>

      {/* Visa ID */}
      <div className="login-usr addVisa">
        <label>Visa ID</label>
        <input
          type="text"
          value={form.id}
          onChange={onChange("id")}
          placeholder="16-character Visa ID"
          maxLength={16}
          required
          aria-invalid={!!errors.id}
          aria-describedby="err-visa-id"
        />
        {errors.id && <small id="err-visa-id" className="error">{errors.id}</small>}
      </div>

      <div className="section-add">
        <div className="login-usr addVisa">
          <label>Country</label>
          <input
            type="text"
            value={form.country}
            onChange={onChange("country")}
            placeholder="Country"
            required
            aria-invalid={!!errors.country}
            aria-describedby="err-country"
          />
        {errors.country && <small id="err-country" className="error">{errors.country}</small>}
        </div>

        <div className="login-password addVisa">
          <label>Visa Type</label>
          <select
            value={form.visaType}
            onChange={onChange("visaType")}
            required
            aria-invalid={!!errors.visaType}
            aria-describedby="err-type"
          >
            <option value="" disabled>Select a visa type</option>
            {VISA_TYPES.map((v) => (
              <option key={v} value={v}>{v}</option>
            ))}
          </select>
          {errors.visaType && <small id="err-type" className="error">{errors.visaType}</small>}
        </div>
      </div>

      <div className="section-add">
        <div className="login-password addVisa">
          <label>Visa Start Date</label>
          <input
            type="date"
            value={form.visaStartDate}
            onChange={onChange("visaStartDate")}
            required
            aria-invalid={!!errors.visaStartDate}
            aria-describedby="err-start"
            // optional UX: constrain expiry min
            max={form.visaExpiryDate || undefined}
          />
          {errors.visaStartDate && <small id="err-start" className="error">{errors.visaStartDate}</small>}
        </div>
        <div className="login-password addVisa">
          <label>Visa Expiry Date</label>
          <input
            type="date"
            value={form.visaExpiryDate}
            onChange={onChange("visaExpiryDate")}
            required
            aria-invalid={!!errors.visaExpiryDate}
            aria-describedby="err-expiry"
            // optional UX: ensure not before start date
            min={form.visaStartDate || undefined}
          />
          {errors.visaExpiryDate && <small id="err-expiry" className="error">{errors.visaExpiryDate}</small>}
        </div>
      </div>

      <div className="section-three">
        <h2>Upload Visa Document</h2>
        <FiUpload />
        <p>Drag and drop a file here or click to upload</p>
      </div>

      <div className="btn-container">
        <button className="btn-addVisa" onClick={onSubmit} disabled={saving}>
          {saving ? "Adding..." : "Add Visa"}
        </button>
        <button className="btn-addVisa cancel" onClick={onCancel}>Cancel</button>
      </div>
    </div>
  );
};

export default AddVisaToHolder;
