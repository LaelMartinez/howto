import React, { useState, useEffect } from "react";
import HowtoForm from "./HowtoForm";
import EndpointForm from "./EndpointForm";
import ImplementationExampleForm from "./ImplementationExampleForm";

const App = () => {
  const [howtos, setHowtos] = useState([]);
  const [selectedHowto, setSelectedHowto] = useState(null);
  const [selectedEndpoint, setSelectedEndpoint] = useState(null);
  const [selectedImplementationExample, setSelectedImplementationExample] = useState(null);
  const [howtoId, setHowtoId] = useState(null);

  // Fetch all Howtos
  useEffect(() => {
    fetchHowtos();
  }, []);

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

  const handleEditEndpoint = (endpoint) => {
    setSelectedEndpoint(endpoint);
  };

  const handleDeleteEndpoint = async (howtoId, endpointId) => {
    try {
      await fetch(`http://localhost:8080/howtos/${howtoId}/endpoints/${endpointId}`, {
        method: "DELETE",
      });
      fetchHowtos(); // Atualizar lista após exclusão
    } catch (error) {
      console.error("Erro ao excluir Endpoint:", error);
    }
  };

  const handleEditImplementationExample = (example) => {
    setSelectedImplementationExample(example);
  };

  const handleDeleteImplementationExample = async (howtoId, exampleId) => {
    try {
      await fetch(`http://localhost:8080/howtos/${howtoId}/implementation-examples/${exampleId}`, {
        method: "DELETE",
      });
      fetchHowtos(); // Atualizar lista após exclusão
    } catch (error) {
      console.error("Erro ao excluir Implementation Example:", error);
    }
  };

  return (
    <div>
      <h1>Gerenciamento de Howtos</h1>

      {/* Formulário de Howto */}
      <HowtoForm fetchHowtos={fetchHowtos} selectedHowto={selectedHowto} />

      <h2>Howtos Cadastrados</h2>
      <ul>
        {howtos.map((howto) => (
          <li key={howto.id}>
            <p>API: {howto.apiName}</p>
            <p>Descrição: {howto.description}</p>
            <button onClick={() => handleEditHowto(howto)}>Editar</button>
            <button onClick={() => handleDeleteHowto(howto.id)}>Excluir</button>

            {/* Formulário de Endpoint para cada Howto */}
            <EndpointForm 
              howtoId={howto.id}
              selectedEndpoint={selectedEndpoint}
              fetchHowtos={fetchHowtos} 
            />

            <h3>Endpoints</h3>
            <ul>
              {howto.endpoints && howto.endpoints.map((endpoint) => (
                <li key={endpoint.id}>
                  <p>URL: {endpoint.url}</p>
                  <p>Método HTTP: {endpoint.httpMethod}</p>
                  <button onClick={() => handleEditEndpoint(endpoint)}>Editar</button>
                  <button onClick={() => handleDeleteEndpoint(howto.id, endpoint.id)}>Excluir</button>
                </li>
              ))}
            </ul>

            {/* Formulário de Implementação para cada Howto */}
            <ImplementationExampleForm 
              howtoId={howto.id}
              selectedImplementationExample={selectedImplementationExample}
              fetchHowtos={fetchHowtos} 
            />

            <h3>Exemplos de Implementação</h3>
            <ul>
              {howto.implementationExamples && howto.implementationExamples.map((example) => (
                <li key={example.id}>
                  <p>Linguagem: {example.language}</p>
                  <p>Código: {example.code}</p>
                  <button onClick={() => handleEditImplementationExample(example)}>Editar</button>
                  <button onClick={() => handleDeleteImplementationExample(howto.id, example.id)}>Excluir</button>
                </li>
              ))}
            </ul>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default App;
