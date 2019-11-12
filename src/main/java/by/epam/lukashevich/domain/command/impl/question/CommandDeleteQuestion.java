package by.epam.lukashevich.domain.command.impl.question;

import by.epam.lukashevich.domain.command.Command;
import by.epam.lukashevich.domain.command.exception.CommandException;
import by.epam.lukashevich.domain.service.QuestionService;
import by.epam.lukashevich.domain.service.ServiceProvider;
import by.epam.lukashevich.domain.service.SubjectService;
import by.epam.lukashevich.domain.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.epam.lukashevich.domain.util.config.BeanFieldJsp.*;
import static by.epam.lukashevich.domain.util.config.JSPActionCommand.VIEW_QUESTION_TABLE;
import static by.epam.lukashevich.domain.util.config.JSPPages.QUESTION_TABLE_PAGE;

public class CommandDeleteQuestion implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, CommandException {
        final HttpSession session = request.getSession();
        final QuestionService service = ServiceProvider.getInstance().getQuestionService();
        final int id = Integer.parseInt(request.getParameter(QUESTION_ID));

        try {
            service.delete(id);
            session.setAttribute(REDIRECT_COMMAND, VIEW_QUESTION_TABLE);
            response.sendRedirect(QUESTION_TABLE_PAGE);
        } catch (ServiceException e) {
            throw new CommandException("Can't delete question in execute() CommandDeleteQuestion", e);
        }
    }
}