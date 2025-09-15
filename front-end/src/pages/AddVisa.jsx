// src/pages/AddVisa.jsx
import React, { useEffect, useState } from "react";
import { FiUpload } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import { createPerson } from "../api/persons";

const emailRe = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

const initialForm = {
  firstName: "",
  lastName: "",
  dateOfBirth: "",
  addressLine1: "",
  postcode: "",
  email: "",
};

const AddVisa = () => {
  const navigate = useNavigate();
  const [form, setForm] = useState(initialForm);
  const [errors, setErrors] = useState({});
  const [saving, setSaving] = useState(false);

  useEffect(() => {
    document.title = `Add Visa Holder - Visa Holder Movement Tracker`;
  }, []);

  useEffect(() => {
    document.body.classList.add("addVisa-body");
    return () => document.body.classList.remove("addVisa-body");
  }, []);

  const onChange = (e) => {
    const { name, value } = e.target;
    setForm((f) => ({ ...f, [name]: value }));
  };

  // Here is the data validation for the form.
  // I am validating the form fields to make sure there are inputs. 
  const validate = (values) => {
    const e = {};
    if (!values.firstName?.trim()) e.firstName = "First name is required.";
    if (!values.lastName?.trim()) e.lastName = "Last name is required.";

    if (!values.dateOfBirth) e.dateOfBirth = "Date of birth is required.";
    else {
      const dob = new Date(values.dateOfBirth);
      const today = new Date();
      if (isNaN(dob.getTime())) e.dateOfBirth = "Date of birth is invalid.";
      else if (dob > today) e.dateOfBirth = "Date of birth cannot be in the future.";
    }

    if (!values.addressLine1?.trim()) e.addressLine1 = "Address is required.";
    if (!values.postcode?.trim()) e.postcode = "Postcode is required.";

    if (!values.email?.trim()) e.email = "Email is required.";
    else if (!emailRe.test(values.email.trim())) e.email = "Email format is invalid.";
    return e;
    
  };

  const onSave = async () => {
    // trim before validating/sending
    const trimmed = {
      firstName: form.firstName.trim(),
      lastName: form.lastName.trim(),
      dateOfBirth: form.dateOfBirth,
      addressLine1: form.addressLine1.trim(),
      postcode: form.postcode.trim(),
      email: form.email.trim(),
    };

    //here is the data validation for the form
    const v = validate(trimmed);
    setErrors(v);
    if (Object.keys(v).length > 0) {
      alert("Please fix the highlighted fields.");
      return;
    }

    try {
      setSaving(true);
      await createPerson(trimmed);
      alert("Saved");
      setForm(initialForm);
      setErrors({});
    } catch (e) {
      console.error(e);
      alert(e?.response?.data || "Failed to Save");
    } finally {
      setSaving(false);
    }
  };

  const onCancel = () => {
    setForm(initialForm);
    setErrors({});
    navigate("/search");
  };

  return (
    <div className="login-container addvisa-con">
      <div className="text-addVisa">
        <h2 className="login-txt addVisa">Add Visa Holder</h2>
      </div>

      <div className="section-add">
        <div className="login-usr addVisa">
          <label>First Name</label>
          <input
            name="firstName"
            value={form.firstName}
            type="text"
            placeholder="First Name"
            onChange={onChange}
            required
            aria-invalid={!!errors.firstName}
            aria-describedby="err-firstName"
          />
          {errors.firstName && <small id="err-firstName" className="error">{errors.firstName}</small>}
        </div>

        <div className="login-password addVisa">
          <label>Last Name</label>
          <input
            type="text"
            name="lastName"
            value={form.lastName}
            placeholder="Last Name"
            onChange={onChange}
            required
            aria-invalid={!!errors.lastName}
            aria-describedby="err-lastName"
          />
          {errors.lastName && <small id="err-lastName" className="error">{errors.lastName}</small>}
        </div>
      </div>

      <div className="section-add-two">
        <div className="login-password addVisa">
          <label>Date of Birth</label>
          <input
            type="date"
            name="dateOfBirth"
            value={form.dateOfBirth}
            onChange={onChange}
            required
            aria-invalid={!!errors.dateOfBirth}
            aria-describedby="err-dob"
          />
          {errors.dateOfBirth && <small id="err-dob" className="error">{errors.dateOfBirth}</small>}
        </div>
      </div>

      <div className="section-add">
        <div className="login-usr addVisa">
          <label>Address</label>
          <input
            type="text"
            name="addressLine1"
            value={form.addressLine1}
            placeholder="Address"
            onChange={onChange}
            required
            aria-invalid={!!errors.addressLine1}
            aria-describedby="err-address"
          />
          {errors.addressLine1 && <small id="err-address" className="error">{errors.addressLine1}</small>}
        </div>

        <div className="login-password addVisa">
          <label>Postcode</label>
          <input
            name="postcode"
            value={form.postcode}
            type="text"
            placeholder="Postcode"
            onChange={onChange}
            required
            aria-invalid={!!errors.postcode}
            aria-describedby="err-postcode"
          />
          {errors.postcode && <small id="err-postcode" className="error">{errors.postcode}</small>}
        </div>
      </div>

      <div className="section-add">
        <div className="login-usr addVisa">
          <label>Email</label>
          <input
            type="email"
            name="email"
            value={form.email}
            placeholder="Email"
            onChange={onChange}
            required
            aria-invalid={!!errors.email}
            aria-describedby="err-email"
          />
          {errors.email && <small id="err-email" className="error">{errors.email}</small>}
        </div>
      </div>

      <div className="section-three">
        <h2>Upload Document</h2>
        <FiUpload />
        <p>Drag and drop a file here or click to upload</p>
      </div>

      <div className="btn-container">
        <button className="btn-addVisa" onClick={onSave} disabled={saving}>
          {saving ? "Saving..." : "Save"}
        </button>
        <button className="btn-addVisa cancel" onClick={onCancel}>Cancel</button>
      </div>
    </div>
  );
};

export default AddVisa;
