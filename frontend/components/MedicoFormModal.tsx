import React from "react";

interface MedicoFormModalProps {
  open: boolean;
  onClose: () => void;
  onSubmit: (data: { nombre: string; especialidad: string }) => void;
  initialData?: { nombre: string; especialidad: string };
  isEdit?: boolean;
}

const MedicoFormModal: React.FC<MedicoFormModalProps> = ({ open, onClose, onSubmit, initialData, isEdit }) => {
  const [nombre, setNombre] = React.useState(initialData?.nombre || "");
  const [especialidad, setEspecialidad] = React.useState(initialData?.especialidad || "");

  React.useEffect(() => {
    setNombre(initialData?.nombre || "");
    setEspecialidad(initialData?.especialidad || "");
  }, [initialData, open]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit({ nombre, especialidad });
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
        <h2 className="text-2xl font-bold mb-4 text-slate-800">{isEdit ? "Editar Médico" : "Agregar Médico"}</h2>
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
            placeholder="Especialidad"
            value={especialidad}
            onChange={(e) => setEspecialidad(e.target.value)}
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

export default MedicoFormModal;
