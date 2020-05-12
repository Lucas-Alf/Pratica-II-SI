export interface Cargo {
  id: number;
  descricao: string;
  cboid: {
    id: string;
    descricaosumaria: string
  };
  faixatabelasalarial: any;
  cargoConhecimentos: any[];
  cargoHabilidadeAtitudes: any[];
}