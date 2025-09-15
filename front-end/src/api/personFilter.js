// utils/personFilter.js
export const filterPersons = (rows, filters) => {
  const q = Object.fromEntries(
    Object.entries(filters).filter(([, v]) => v != null && String(v).trim() !== "")
  );
  const toStr = (v) => (v == null ? "" : String(v)).toLowerCase();

  return rows.filter((p) => {
    if (q.firstName && !toStr(p.firstName).includes(toStr(q.firstName))) return false;
    if (q.lastName && !toStr(p.lastName).includes(toStr(q.lastName))) return false;
    if (q.email && !toStr(p.email).includes(toStr(q.email))) return false;
    if (q.postcode && !toStr(p.postcode).includes(toStr(q.postcode))) return false;
    if (q.addressLine1 && !toStr(p.addressLine1).includes(toStr(q.addressLine1))) return false;
    if (q.dateOfBirth && toStr(p.dateOfBirth) !== toStr(q.dateOfBirth)) return false; // exact date
    return true;
  });
};
