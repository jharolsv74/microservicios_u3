
"use client";
import React, { useState } from "react";

import Sidebar from "../../components/Sidebar";
import MedicosDashboard from "../../components/MedicosDashboard";
import TurnosDashboard from "../../components/TurnosDashboard";
import PacientesDashboard from "../../components/PacientesDashboard";
import NotificacionesDashboard from "../../components/NotificacionesDashboard";

export default function Home() {
  const [selected, setSelected] = useState("medicos");

  return (
    <div className="flex min-h-screen bg-gray-50">
      <Sidebar onSelect={setSelected} selected={selected} />
      <main className="flex-1">
        {selected === "medicos" && <MedicosDashboard />}
        {selected === "turnos" && <TurnosDashboard />}
        {selected === "pacientes" && <PacientesDashboard />}
        {selected === "notificaciones" && <NotificacionesDashboard />}
      </main>
    </div>
  );
}
