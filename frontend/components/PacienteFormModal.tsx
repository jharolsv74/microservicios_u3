import React from "react";

interface PacienteFormModalProps {
  open: boolean;
  onClose: () => void;
  onSubmit: (data: { nombre: string; apellido: string; email: string; telefono: string }) => void;
  initialData?: { nombre: string; apellido: string; email: string; telefono: string };
  isEdit?: boolean;
}

const PacienteFormModal: React.FC<PacienteFormModalProps> = ({ open, onClose, onSubmit, initialData, isEdit }) => {
  const [nombre, setNombre] = React.useState(initialData?.nombre || "");
  const [apellido, setApellido] = React.useState(initialData?.apellido || "");
  const [email, setEmail] = React.useState(initialData?.email || "");
  const [telefono, setTelefono] = React.useState(initialData?.telefono || "");

  React.useEffect(() => {
    setNombre(initialData?.nombre || "");
    setApellido(initialData?.apellido || "");
    setEmail(initialData?.email || "");
    setTelefono(initialData?.telefono || "");
  }, [initialData, open]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit({ nombre, apellido, email, telefono });
  };

  if (!open) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-30">
      <div className="bg-white rounded-lg shadow-lg w-full max-w-md p-6 relative">
        <button
          className="absolute top-2 right-2 text-gray-400 hover:text-gray-600 text-xl"
          onClick={onClose}
        >
          ×
        </button>
        <h2 className="text-2xl font-bold mb-4 text-slate-800">{isEdit ? "Editar Paciente" : "Agregar Paciente"}</h2>
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <input
            type="text"
            placeholder="Nombre"
            value={nombre}
            onChange={(e) => setNombre(e.target.value)}
            className="border border-slate-300 rounded px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-200"
            required
          />
          <input
            type="text"
            placeholder="Apellido"
            value={apellido}
            onChange={(e) => setApellido(e.target.value)}
            className="border border-slate-300 rounded px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-200"
            required
          />
          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="border border-slate-300 rounded px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-200"
            required
          />
          <input
            type="text"
            placeholder="Teléfono"
            value={telefono}
            onChange={(e) => setTelefono(e.target.value)}
            className="border border-slate-300 rounded px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-200"
            required
          />
          <div className="flex justify-end gap-2 mt-2">
            <button
              type="button"
              className="px-4 py-2 rounded bg-gray-100 text-gray-700 hover:bg-gray-200"
              onClick={onClose}
            >
              Cancelar
            </button>
            <button
              type="submit"
              className="px-4 py-2 rounded bg-blue-600 text-white font-semibold hover:bg-blue-700 shadow-sm"
            >
              {isEdit ? "Actualizar" : "Agregar"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default PacienteFormModal;
