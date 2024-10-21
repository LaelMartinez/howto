import React, { useState, useEffect } from "react";

const ImplementationExampleForm = ({ howtoId, fetchImplementationExamples, selectedImplementationExample }) => {
  const [language, setLanguage] = useState("");
  const [code, setCode] = useState("");

  useEffect(() => {
    if (selectedImplementationExample) {
      setLanguage(selectedImplementationExample.language);
      setCode(selectedImplementationExample.code);
    }
  }, [selectedImplementationExample]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const method = selectedImplementationExample ? "PUT" : "POST";
    const urlPath = selectedImplementationExample
      ? `http://localhost:8080/howtos/${howtoId}/implementation-examples/${selectedImplementationExample.id}`
      : `http://localhost:8080/howtos/${howtoId}/implementation-examples`;

    try {
      await fetch(urlPath, {
        method: method,
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          language,
          code,
        }),
      });
      fetchImplementationExamples(); // Atualizar lista de ImplementationExamples
      setLanguage("");
      setCode("");
    } catch (error) {
      console.error("Erro ao salvar Implementation Example:", error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>{selectedImplementationExample ? "Editar Implementação" : "Nova Implementação"}</h2>
      <div>
        <label>Linguagem:</label>
        <input
          type="text"
          value={language}
          onChange={(e) => setLanguage(e.target.value)}
          required
        />
      </div>
      <div>
        <label>Código:</label>
        <textarea
          value={code}
          onChange={(e) => setCode(e.target.value)}
          required
        />
      </div>
      <button type="submit">{selectedImplementationExample ? "Atualizar" : "Cadastrar"}</button>
    </form>
  );
};

export default ImplementationExampleForm;
