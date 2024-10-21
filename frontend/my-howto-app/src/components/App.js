import React, { useState, useEffect } from "react";
import HowtoForm from "./HowtoForm";
import EndpointForm from "./EndpointForm";

const App = () => {
  const [howtos, setHowtos] = useState([]);
  const [selectedHowto, setSelectedHowto] = useState(null);
  const [selectedEndpoint, setSelectedEndpoint] = useState(null);
  const [selectedImplementationExample, setSelectedImplementationExample] = useState(null);
  const [howtoId, setHowtoId] = useState(null);

  // Fetch all Howtos
  useEffect(() => {
    if (howtoId) {
      fetchHowtos();
    }
  }, [howtoId]);

  const fetchHowtos = async () => {
    try {
      const response = await fetch("http://localhost:8080/howtos");
      const data = await response.json();
      setHowtos(data);
    } catch (error) {
      console.error("Erro ao buscar Howtos:", error);
    }
  };

  const handleEditHowto = (howto) => {
    setSelectedHowto(howto);
    setHowtoId(howto.id);
  };

  const handleDeleteHowto = async (id) => {
    try {
      await fetch(`http://localhost:8080/howtos/${id}`, {
        method: "DELETE",
      });
      fetchHowtos(); // Atualizar lista após exclusão
    } catch (error) {
      console.error("Erro ao excluir Howto:", error);
    }
  };

  return (
    <div>
      <h1>Gerenciamento de Howtos</h1>
      <HowtoForm fetchHowtos={fetchHowtos} selectedHowto={selectedHowto} />
      <h2>Howtos Cadastrados</h2>
      <ul>
        {howtos.map((howto) => (
          <li key={howto.id}>
            <p>API: {howto.apiName}</p>
            <p>Descrição: {howto.description}</p>
            <button onClick={() => handleEditHowto(howto)}>Editar</button>
            <button onClick={() => handleDeleteHowto(howto.id)}>Excluir</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default App;
