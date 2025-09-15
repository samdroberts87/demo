import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { FaUser } from "react-icons/fa";
import { FaUnlockAlt } from "react-icons/fa";
import { useState } from "react";
import { useEffect } from "react";






const LoginPage = () => {

          useEffect(() => {
            document.title = `Login - Visa Holder Movement Tracker`;
            }, []);

  const [ username , setUsername] = useState("");
  const [ password, setPassword] = useState("");



  const navigate = useNavigate()



  function handleSubmit(e) {
    e.preventDefault();
    if (username === "james" && password === "P1r1n01a!") {
      navigate("/search");
    } else {
      alert("Invalid username or password");
      navigate("/");
    }
  }
  



  return (
    <div className="visa-login-container">
      <h2 className="login-title">Visa Holder Movement Tracker</h2>
      <div className="login-container">
      
      <h1 className="login-txt">L
        ogin</h1>
      <div className="login-usr">
        <input type="text" placeholder="Username" id="username" value={username} onChange={(v) => setUsername(v.target.value)}  />
        <FaUser />

      </div>
      <div className="login-password">
        <input  type="password" placeholder="Password" id="password" value={password}  onChange={(v) => setPassword(v.target.value)} />
        <FaUnlockAlt />
      </div>

      <button type="submit" onClick={handleSubmit}>Submit</button>
    </div>
    </div>

  );
};

export default LoginPage;
