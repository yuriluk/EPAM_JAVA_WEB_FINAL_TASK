package by.epam.lukashevich.domain.command.impl.subject;

import by.epam.lukashevich.domain.command.Command;
import by.epam.lukashevich.domain.command.exception.CommandException;
import by.epam.lukashevich.domain.entity.Subject;
import by.epam.lukashevich.domain.service.ServiceProvider;
import by.epam.lukashevich.domain.service.SubjectService;
import by.epam.lukashevich.domain.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static by.epam.lukashevich.domain.util.config.BeanFieldJsp.SUBJECT_LIST;
import static by.epam.lukashevich.domain.util.config.JSPPages.SUBJECT_TABLE_PAGE;

public class CommandViewSubjectTable implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, CommandException {

        final HttpSession session = request.getSession();
        final SubjectService service = ServiceProvider.getInstance().getSubjectService();

        try {
            List<Subject> list = service.findAll();
            request.setAttribute(SUBJECT_LIST, list);
            request.getRequestDispatcher(SUBJECT_TABLE_PAGE).forward(request, response);
        } catch (ServiceException e) {
            throw new CommandException("Can't get list of subjects in execute() CommandViewSubjectTable", e);
        }
    }
}