import React, { useState, useEffect } from "react";

const EndpointForm = ({ howtoId, fetchEndpoints, selectedEndpoint }) => {
  const [url, setUrl] = useState("");
  const [httpMethod, setHttpMethod] = useState("GET");
  const [inputExample, setInputExample] = useState("");
  const [outputExample, setOutputExample] = useState("");

  useEffect(() => {
    if (selectedEndpoint) {
      setUrl(selectedEndpoint.url);
      setHttpMethod(selectedEndpoint.httpMethod);
      setInputExample(selectedEndpoint.inputExample);
      setOutputExample(selectedEndpoint.outputExample);
    }
  }, [selectedEndpoint]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const method = selectedEndpoint ? "PUT" : "POST";
    const urlPath = selectedEndpoint
      ? `http://localhost:8080/howtos/${howtoId}/endpoints/${selectedEndpoint.id}`
      : `http://localhost:8080/howtos/${howtoId}/endpoints`;

    try {
      await fetch(urlPath, {
        method: method,
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          url,
          httpMethod,
          inputExample,
          outputExample,
        }),
      });
      fetchEndpoints(); // Atualizar lista de Endpoints
      setUrl("");
      setHttpMethod("GET");
      setInputExample("");
      setOutputExample("");
    } catch (error) {
      console.error("Erro ao salvar o Endpoint:", error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>{selectedEndpoint ? "Editar Endpoint" : "Novo Endpoint"}</h2>
      <div>
        <label>URL:</label>
        <input
          type="text"
          value={url}
          onChange={(e) => setUrl(e.target.value)}
          required
        />
      </div>
      <div>
        <label>Método HTTP:</label>
        <select
          value={httpMethod}
          onChange={(e) => setHttpMethod(e.target.value)}
        >
          <option value="GET">GET</option>
          <option value="POST">POST</option>
          <option value="PUT">PUT</option>
          <option value="DELETE">DELETE</option>
        </select>
      </div>
      <div>
        <label>Exemplo de Entrada:</label>
        <textarea
          value={inputExample}
          onChange={(e) => setInputExample(e.target.value)}
        />
      </div>
      <div>
        <label>Exemplo de Saída:</label>
        <textarea
          value={outputExample}
          onChange={(e) => setOutputExample(e.target.value)}
        />
      </div>
      <button type="submit">{selectedEndpoint ? "Atualizar" : "Cadastrar"}</button>
    </form>
  );
};

export default EndpointForm;
