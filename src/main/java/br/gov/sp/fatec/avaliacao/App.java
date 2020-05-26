package br.gov.sp.fatec.avaliacao;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.gov.sp.fatec.avaliacao.entity.Aluno;
import br.gov.sp.fatec.avaliacao.entity.Professor;
import br.gov.sp.fatec.avaliacao.entity.Trabalho;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("avaliacao");
        EntityManager manager = factory.createEntityManager();
        
        Professor professor = new Professor();
        professor.setNomeUsuario("mineda");
        professor.setSenha("senha");
        
        Aluno aluno = new Aluno();
        aluno.setNomeUsuario("aluno");
        aluno.setSenha("senha");
        aluno.setRa(1234567891011L);
        
        Trabalho trabalho = new Trabalho();
        trabalho.setTitulo("JPA");
        trabalho.setLocalArquivo("\\uploads\\trab1.pdf");
        trabalho.setProfessor(professor);
        trabalho.setDataHoraEntrega(new Date());
        trabalho.setAlunos(new HashSet<Aluno>());
        trabalho.getAlunos().add(aluno);

        manager.getTransaction().begin();
        manager.persist(professor);
        manager.persist(aluno);
        manager.persist(trabalho);
        manager.getTransaction().commit();
        
        String queryText = "select t " +
                "from Trabalho t " +
                "where t.titulo = :titulo";

        Query query = manager.createQuery(queryText);
        query.setParameter("titulo", "JPA");
                
        @SuppressWarnings("unchecked")
        List<Trabalho> resultados = query.getResultList();

        for(Trabalho trab: resultados) {
            System.out.println("Título: " + trab.getTitulo());
            System.out.println("Path: " + trab.getLocalArquivo());
            System.out.println("Avaliador: " + 
                    trab.getProfessor().getNomeUsuario());
            for(Aluno al: trab.getAlunos()) {
                System.out.println("Aluno: " + 
                        al.getNomeUsuario());
            }
        }
        
        manager.close();       

    }
}
