import React, { useEffect, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { FiUpload } from "react-icons/fi";
import { getPersonById, updatePerson, deletePerson } from "../api/persons";

const emailRe = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

const emptyForm = {
  firstName: "",
  lastName: "",
  addressLine1: "",
  postcode: "",
  email: "",
  dateOfBirth: "",
};

const EditVisa = () => {
  const { id } = useParams();
  const location = useLocation();
  const navigate = useNavigate();

  const [form, setForm] = useState(emptyForm);
  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [removing, setRemoving] = useState(false);
  const [err, setErr] = useState(null);

  useEffect(() => {
    const fromState = location.state?.person;
    if (fromState) {
      setForm({ ...emptyForm, ...fromState });
      setLoading(false);
    }

    (async () => {
      try {
        const fresh = await getPersonById(id);
        if (fresh) setForm({ ...emptyForm, ...fresh });
      } catch (e) {
        if (!fromState) setErr(e?.response?.data || e.message);
      } finally {
        setLoading(false);
      }
    })();
  }, [id, location.state]);

  const onChange = (field) => (e) =>
    setForm((prev) => ({ ...prev, [field]: e.target.value }));

  const onCancel = () => navigate(-1);

  // here is the data validation for the form
  const validate = (v) => {
    const e = {};
    if (!v.firstName?.trim()) e.firstName = "First name is required.";
    if (!v.lastName?.trim()) e.lastName = "Last name is required.";
    if (!v.addressLine1?.trim()) e.addressLine1 = "Address is required.";
    if (!v.postcode?.trim()) e.postcode = "Postcode is required.";
    if (!v.email?.trim()) e.email = "Email is required.";
    else if (!emailRe.test(v.email.trim())) e.email = "Email is invalid.";
    if (!v.dateOfBirth) e.dateOfBirth = "Date of birth is required.";
    else {
      const dob = new Date(v.dateOfBirth);
      const today = new Date();
      if (isNaN(dob.getTime())) e.dateOfBirth = "Date of birth is invalid.";
      else if (dob > today) e.dateOfBirth = "Date of birth cannot be in the future.";
    }
    return e;
  };

  const onUpdate = async () => {
    const trimmed = {
      id: Number(id),
      firstName: form.firstName.trim(),
      lastName: form.lastName.trim(),
      dateOfBirth: form.dateOfBirth,
      addressLine1: form.addressLine1.trim(),
      postcode: form.postcode.trim(),
      email: form.email.trim(),
    };

    // here is the data validation for the form
    const v = validate(trimmed);
    setErrors(v);
    if (Object.keys(v).length) {
      alert("Please fix the highlighted fields.");
      return;
    }

    try {
      setSaving(true);
      const updated = await updatePerson(trimmed);
      console.log("Updated person:", updated);
      alert("Updated successfully!");
      navigate(-1);
    } catch (e) {
      alert(`Update failed: ${e?.response?.data || e.message}`);
    } finally {
      setSaving(false);
    }
  };

  const onRemove = async () => {
    if (removing) return;
    if (!window.confirm("Are you sure you want to remove this visa holder? This cannot be undone.")) return;
    try {
      setRemoving(true);
      await deletePerson(Number(id));
      alert("Visa holder removed.");
      navigate("/search");
    } catch (e) {
      alert(`Failed to remove: ${e?.response?.data || e.message}`);
    } finally {
      setRemoving(false);
    }
  };

  if (loading) return <div className="login-container addvisa-con">Loading…</div>;
  if (err) return <div className="login-container addvisa-con">Error: {String(err)}</div>;

  return (
    <div className="login-container addvisa-con">
      <div className="text-addVisa">
        <h2 className="login-txt addVisa">Edit Visa Holder</h2>
      </div>

      <div className="section-add">
        <div className="login-usr addVisa">
          <label>First Name</label>
          <input
            name="firstName"
            type="text"
            value={form.firstName}
            onChange={onChange("firstName")}
            placeholder="First name"
            required
            aria-invalid={!!errors.firstName}
            aria-describedby="err-firstName"
          />
          {errors.firstName && <small id="err-firstName" className="error">{errors.firstName}</small>}
        </div>

        <div className="login-password addVisa">
          <label>Last Name</label>
          <input
            name="lastName"
            type="text"
            value={form.lastName}
            onChange={onChange("lastName")}
            placeholder="Last name"
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
            name="dateOfBirth"
            type="date"
            value={form.dateOfBirth}
            onChange={onChange("dateOfBirth")}
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
            name="addressLine1"
            type="text"
            value={form.addressLine1}
            onChange={onChange("addressLine1")}
            placeholder="Address"
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
            className="postcode-input"
            type="text"
            value={form.postcode}
            onChange={onChange("postcode")}
            placeholder="Postcode"
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
            name="email"
            className="email-input"
            type="email"
            value={form.email}
            onChange={onChange("email")}
            placeholder="Email"
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
        <button className="btn-addVisa" onClick={onUpdate} disabled={saving}>
          {saving ? "Saving..." : "Update"}
        </button>
        <button className="btn-addVisa" onClick={onRemove} disabled={removing}>
          {removing ? "Removing..." : "Remove"}
        </button>
        <button className="btn-addVisa cancel" onClick={onCancel}>
          Cancel
        </button>
      </div>
    </div>
  );
};

export default EditVisa;
