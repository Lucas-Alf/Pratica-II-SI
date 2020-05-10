export interface Usuario {
    id: number;
    email: string;
    senha: string;
    pessoa: {
      cpf:string; 
      nome:string; 
      sexo:string; 
      rg:string; 
      datanascimento:Date; 
      paisnascimentoid:number; 
      telefonecelular:string; 
      telefonefixo:string; 
      //foto:string; 
      /*
      pispasep:number; 
      pisexpedicao:Date; 
      cnhnumero:number; 
      cnhdata:Date; 
      chntipo:string; 
      ctpsnumero:number; 
      ctpsserie:number; 
      ctpsuf:string; 
      nomepai:string; 
      nomemae:string; 
      tituloeleitornumero:number; 
      tituloeleitoruf:string; 
      tituloeleitorzona:number; 
      tituloeleitorsecao:string; 
      certificadoreservista:number; 
      enderecoid:number; 
      email:string
      */
    };
    nivel:{
      id:number;
      descricao:string
    };
  }
  