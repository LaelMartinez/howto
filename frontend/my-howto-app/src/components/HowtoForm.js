import React, { useState, useEffect } from "react";

const HowtoForm = ({ fetchHowtos, selectedHowto }) => {
  const [apiName, setApiName] = useState("");
  const [description, setDescription] = useState("");

  useEffect(() => {
    if (selectedHowto) {
      setApiName(selectedHowto.apiName);
      setDescription(selectedHowto.description);
    }
  }, [selectedHowto]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const method = selectedHowto ? "PUT" : "POST";
    const url = selectedHowto
      ? `http://localhost:8080/howtos/${selectedHowto.id}`
      : "http://localhost:8080/howtos";

    try {
      await fetch(url, {
        method: method,
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          apiName,
          description,
        }),
      });
      fetchHowtos(); // Atualizar lista de Howtos
      setApiName("");
      setDescription("");
    } catch (error) {
      console.error("Erro ao salvar o Howto:", error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>{selectedHowto ? "Editar Howto" : "Novo Howto"}</h2>
      <div>
        <label>API Name:</label>
        <input
          type="text"
          value={apiName}
          onChange={(e) => setApiName(e.target.value)}
          required
        />
      </div>
      <div>
        <label>Descrição:</label>
        <textarea
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          required
        />
      </div>
      <button type="submit">{selectedHowto ? "Atualizar" : "Cadastrar"}</button>
    </form>
  );
};

export default HowtoForm;
