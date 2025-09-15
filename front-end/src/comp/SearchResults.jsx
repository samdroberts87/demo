// comp/SearchResults.jsx
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getAllPersons, getPersonById } from "../api/persons";
import { filterPersons } from "../api/personFilter";

const PAGE_SIZE = 5; // <-- limit to 10 per page

const SearchResults = ({ searchData }) => {
  const navigate = useNavigate();

  const [rows, setRows] = useState([]);
  const [loading, setLoading] = useState(false);
  const [err, setErr] = useState(null);
  const [hasSearched, setHasSearched] = useState(false);

  // pagination state
  const [page, setPage] = useState(1); // <-- current page (1-based)

  useEffect(() => {
    if (!searchData) return; // start with no results

    const nonEmpty = Object.fromEntries(
      Object.entries(searchData).filter(
        ([, v]) => v != null && String(v).trim() !== ""
      )
    );
    if (Object.keys(nonEmpty).length === 0) return;

    setHasSearched(true);
    setLoading(true);
    setErr(null);

    const byId = nonEmpty.id && String(nonEmpty.id).trim() !== "";

    (async () => {
      try {
        if (byId) {
          const person = await getPersonById(nonEmpty.id);
          setRows(person ? [person] : []);
        } else {
          const all = await getAllPersons();
          setRows(filterPersons(all, nonEmpty));
        }
        setPage(1); // <-- reset to page 1 whenever a new search runs
      } catch (e) {
        setErr(e?.response?.data || e.message || "Request failed");
        setRows([]);
        setPage(1);
      } finally {
        setLoading(false);
      }
    })();
  }, [searchData]);

  const handleEdit = (person) => {
    navigate(`/editVisa/${person.id}`, { state: { person } });
  };

  const handleView = (person) => {
    navigate(`/visaHolderDetails/${person.id}`, { state: { person } });
  };

  if (!hasSearched) return null;

  // ------ pagination calculations ------
  const total = rows.length;
  const totalPages = Math.max(1, Math.ceil(total / PAGE_SIZE));
  const currentPage = Math.min(page, totalPages);
  const startIndex = (currentPage - 1) * PAGE_SIZE;
  const endIndex = Math.min(startIndex + PAGE_SIZE, total);
  const pageRows = rows.slice(startIndex, endIndex);

  const canPrev = currentPage > 1;
  const canNext = currentPage < totalPages;

  const gotoPrev = () => canPrev && setPage((p) => p - 1);
  const gotoNext = () => canNext && setPage((p) => p + 1);

  return (
    <>
      <table>
        <caption>Search Results</caption>
        <thead>
          <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>DOB</th>
            <th>Address</th>
            <th>Postcode</th>
            <th>Email</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {loading && (
            <tr>
              <td colSpan="8">Loading…</td>
            </tr>
          )}
          {!loading && err && (
            <tr>
              <td colSpan="8">Error: {String(err)}</td>
            </tr>
          )}
          {!loading && !err && total === 0 && (
            <tr>
              <td colSpan="8">No results</td>
            </tr>
          )}
          {!loading &&
            !err &&
            total > 0 &&
            pageRows.map((p) => (
              <tr key={p.id}>
                <td>{p.id}</td>
                <td>{p.firstName || "—"}</td>
                <td>{p.lastName || "—"}</td>
                <td>{p.dateOfBirth || "—"}</td>
                <td>{p.addressLine1 || "—"}</td>
                <td>{p.postcode || "—"}</td>
                <td>{p.email || "—"}</td>
                <td>
                  <button className="EditButton" onClick={() => handleEdit(p)}>Edit</button>
                  <button className="ViewButton" onClick={() => handleView(p)}>View</button>
                </td>
              </tr>
            ))}
        </tbody>
      </table>

      {/* Pagination controls (only show if more than one page) */}
      {!loading && !err && total > PAGE_SIZE && (
        <div className="pagination">
          <span>
            Showing <strong>{total === 0 ? 0 : startIndex + 1}</strong>–<strong>{endIndex}</strong> of <strong>{total}</strong>
          </span>
          <div style={{ display: "flex", gap: 8 }}>
            <button onClick={gotoPrev} disabled={!canPrev} aria-label="Previous page">
              ‹ Prev
            </button>
            <span className="page-number">
              Page {currentPage} of {totalPages}
            </span>
            <button onClick={gotoNext} disabled={!canNext} aria-label="Next page">
              Next ›
            </button>
          </div>
        </div>
      )}
    </>
  );
};

export default SearchResults;
