package br.com.alura.agenda.asynctask;

import java.util.List;

import br.com.alura.agenda.database.dao.AlunoDAO;
import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;

public class EditaAlunoTask extends BaseAlunoComTelefoneTask {

    private final AlunoDAO alunoDAO;
    private final Aluno aluno;
    private final Telefone telefoneCelular;
    private final Telefone telefoneFixo;
    private final TelefoneDAO telefoneDAO;
    private final List<Telefone> telefones;

    public EditaAlunoTask(AlunoDAO alunoDAO, Aluno aluno, Telefone telefoneCelular, Telefone telefoneFixo, TelefoneDAO telefoneDAO, List<Telefone> telefones, FinalizadaListener listener) {
        super(listener);
        this.alunoDAO = alunoDAO;
        this.aluno = aluno;
        this.telefoneCelular = telefoneCelular;
        this.telefoneFixo = telefoneFixo;
        this.telefoneDAO = telefoneDAO;
        this.telefones = telefones;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        alunoDAO.edita(aluno);
        vincluaAlunoTelefone(aluno.getId(), telefoneCelular, telefoneFixo);
        atualizaIdsTelefone(telefoneFixo, telefoneCelular);

        return null;
    }

    private void atualizaIdsTelefone(Telefone telefoneFixo, Telefone telefoneCelular) {
        for (Telefone telefone :
                telefones) {
            switch (telefone.getTipo()) {
                case FIXO:
                    telefoneFixo.setId(telefone.getId());
                case CELULAR:
                    telefoneCelular.setId(telefone.getId());
            }
        }
        telefoneDAO.atualiza(telefoneFixo, telefoneCelular);
    }

}
