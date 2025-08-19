
import React, { useEffect, useState } from "react";

const icons = {
  medicos: (
    <svg className="h-6 w-6 text-blue-500" fill="none" stroke="currentColor" strokeWidth={2} viewBox="0 0 24 24">
      <path d="M4.8 2.3A.3.3 0 1 0 5 2a3.3 3.3 0 0 1 0 6.6v8.4a3.3 3.3 0 0 1-3.3 3.3.3.3 0 1 0 .2.6A3.3 3.3 0 0 1 5 23a.3.3 0 1 0 .6-.2A3.3 3.3 0 0 1 2.3 20v-8.4a3.3 3.3 0 0 1 3.3-3.3H8V2.3A.3.3 0 0 0 4.8 2.3Z" />
      <path d="M8 8V2.3A.3.3 0 1 0 7.8 2c-1.3 0-2.3 1.1-2.3 2.3v13.4A2.3 2.3 0 0 0 7.8 20a.3.3 0 1 0 .4.5A2.3 2.3 0 0 0 10 17.7V8Z" />
      <path d="M16 2.3a.3.3 0 1 0 .2-.6A3.3 3.3 0 0 0 12.9 5v12.7a2.3 2.3 0 0 0 2.3 2.3.3.3 0 1 0 .4-.5A2.3 2.3 0 0 0 13.2 17V5a3.3 3.3 0 0 0 3.3-3.3.3.3 0 1 0-.5-.4Z" />
      <path d="m19 2-2 6" />
      <path d="m22 5-6-2" />
      <circle cx="19" cy="8" r="3" />
    </svg>
  ),
  turnos: (
    <svg className="h-6 w-6 text-slate-400" fill="none" stroke="currentColor" strokeWidth={2} viewBox="0 0 24 24">
      <rect height="18" rx="2" ry="2" width="18" x="3" y="4" />
      <line x1="16" x2="16" y1="2" y2="6" />
      <line x1="8" x2="8" y1="2" y2="6" />
      <line x1="3" x2="21" y1="10" y2="10" />
      <circle cx="8" cy="14" r="1" />
      <circle cx="12" cy="14" r="1" />
      <circle cx="16" cy="14" r="1" />
      <circle cx="8" cy="18" r="1" />
      <circle cx="12" cy="18" r="1" />
      <circle cx="16" cy="18" r="1" />
    </svg>
  ),
  pacientes: (
    <svg className="h-6 w-6 text-slate-400" fill="none" stroke="currentColor" strokeWidth={2} viewBox="0 0 24 24">
      <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2" />
      <circle cx="9" cy="7" r="4" />
      <path d="M22 21v-2a4 4 0 0 0-3-3.87" />
      <path d="M16 3.13a4 4 0 0 1 0 7.75" />
    </svg>
  ),
  notificaciones: (
    <svg className="h-6 w-6 text-slate-400" fill="none" stroke="currentColor" strokeWidth={2} viewBox="0 0 24 24">
      <path d="M6 8a6 6 0 0 1 12 0c0 7 3 9 3 9H3s3-2 3-9" />
      <path d="M10.3 21a1.94 1.94 0 0 0 3.4 0" />
    </svg>
  ),
  config: (
    <svg className="h-6 w-6 text-slate-400" fill="none" stroke="currentColor" strokeWidth={2} viewBox="0 0 24 24">
      <circle cx="12" cy="12" r="3" />
      <path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1-2.83 2.83l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-4 0v-.09A1.65 1.65 0 0 0 7 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 1 1-2.83-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1 0-4h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 1 1 2.83-2.83l.06.06A1.65 1.65 0 0 0 7 4.6a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 4 0v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 1 1 2.83 2.83l-.06.06A1.65 1.65 0 0 0 19.4 9c.14.31.22.65.22 1s-.08.69-.22 1z" />
    </svg>
  ),
};


interface SidebarProps {
  onSelect: (section: string) => void;
  selected: string;
}

const Sidebar: React.FC<SidebarProps> = ({ onSelect, selected }) => {
  const items = [
    { label: "Médicos", key: "medicos", icon: icons.medicos },
    { label: "Turnos", key: "turnos", icon: icons.turnos },
    { label: "Pacientes", key: "pacientes", icon: icons.pacientes },
  ];
    const [notiCount, setNotiCount] = useState(0);

    useEffect(() => {
      fetch("http://localhost:8084/notificaciones")
        .then((res) => res.json())
        .then((data) => {
          const arr = Array.isArray(data) ? data : (data.content || []);
          setNotiCount(arr.length);
        });
    }, []);

    return (
      <aside className="w-64 flex-shrink-0 bg-white border-r border-slate-200 flex flex-col justify-between p-4 min-h-screen">
        <div>
          <div className="px-4 mb-8 flex items-center gap-2">
            <svg className="h-8 w-8 text-blue-600" fill="none" stroke="currentColor" strokeWidth={2} viewBox="0 0 24 24">
              <path d="M12 2L2 7l10 5 10-5-10-5z" />
              <path d="M2 17l10 5 10-5" />
              <path d="M2 12l10 5 10-5" />
            </svg>
            <h1 className="text-xl font-bold text-slate-800">ClínicaGes</h1>
          </div>
          <nav className="flex flex-col gap-2">
            {items.map((item) => (
              <button
                key={item.key}
                className={`flex items-center gap-4 px-4 py-2.5 rounded-md text-sm font-semibold transition-colors duration-200 w-full text-left ${selected === item.key ? "bg-blue-50 text-blue-700" : "text-slate-500 hover:bg-slate-100 hover:text-slate-800"}`}
                onClick={() => onSelect(item.key)}
              >
                {item.icon}
                <span>{item.label}</span>
              </button>
            ))}
          </nav>
        </div>
        <div className="flex flex-col gap-2">
          <button className="flex items-center gap-4 px-4 py-2.5 rounded-md text-sm font-semibold transition-colors duration-200 w-full text-left text-slate-500 hover:bg-slate-100 hover:text-slate-800 relative" onClick={() => onSelect('notificaciones')}>
            {icons.notificaciones}
            <span>Notificaciones</span>
            {notiCount > 0 && (
              <span className="absolute right-4 top-1 flex h-3 w-3">
                <span className="animate-ping absolute inline-flex h-full w-full rounded-full bg-blue-500 opacity-75"></span>
                <span className="relative inline-flex rounded-full h-3 w-3 bg-blue-500"></span>
              </span>
            )}
          </button>
          <button className="flex items-center gap-4 px-4 py-2.5 rounded-md text-sm font-semibold transition-colors duration-200 w-full text-left text-slate-500 hover:bg-slate-100 hover:text-slate-800" onClick={() => alert('Configuración')}>
            {icons.config}
            <span>Configuración</span>
          </button>
        </div>
      </aside>
    );
};

export default Sidebar;
