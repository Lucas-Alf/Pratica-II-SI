export interface VagaPessoa {
  id: number;
  cpf: {
    cpf: string,
    nome: string,
    sexo: string,
    telefonecelular: number,
    datanascimento: Date,
    numero: number,
    enderecoid: {
      id: number,
    },
    pessoaIdiomas: any[];
    pessoaConhecimentos: any[];
    pessoaHabilidadesAtitudes: any[];
  },
  vagaid: number;
  experienciaprofissional: string,
}